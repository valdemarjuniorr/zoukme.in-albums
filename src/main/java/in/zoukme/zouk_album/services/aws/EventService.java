package in.zoukme.zouk_album.services.aws;

import in.zoukme.zouk_album.domains.EventPhotos;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.domains.Photo;
import in.zoukme.zouk_album.domains.SocialMedia;
import in.zoukme.zouk_album.domains.SubEvent;
import in.zoukme.zouk_album.domains.subevent.SubEventWithEvent;
import in.zoukme.zouk_album.exceptions.EventNotFoundException;
import in.zoukme.zouk_album.exceptions.SubEventNotFoundException;
import in.zoukme.zouk_album.repositories.PhotoRepository;
import in.zoukme.zouk_album.repositories.SocialMediaRepository;
import in.zoukme.zouk_album.repositories.events.CreateEventRequest;
import in.zoukme.zouk_album.repositories.events.EventDetails;
import in.zoukme.zouk_album.repositories.events.EventPhotosRepository;
import in.zoukme.zouk_album.repositories.events.EventRepository;
import in.zoukme.zouk_album.repositories.events.SubEventRepository;
import in.zoukme.zouk_album.repositories.events.UpdateEventRequest;
import in.zoukme.zouk_album.services.PackageService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import in.zoukme.zouk_album.domains.Event;

@Service
public class EventService {

  private final EventRepository repository;
  private final SocialMediaRepository socialMediaRepository;
  private final PhotoRepository photoRepository;
  private final BucketService bucketService;
  private final SubEventRepository subEventRepository;
  private final EventPhotosRepository eventPhotosRepository;
  private final PackageService packageService;

  public EventService(
      EventRepository repository,
      SocialMediaRepository socialMediaRepository,
      PhotoRepository photoRepository,
      BucketService bucketService,
      SubEventRepository subEventRepository,
      EventPhotosRepository eventPhotosRepository,
      PackageService packageService) {
    this.repository = repository;
    this.socialMediaRepository = socialMediaRepository;
    this.photoRepository = photoRepository;
    this.bucketService = bucketService;
    this.subEventRepository = subEventRepository;
    this.eventPhotosRepository = eventPhotosRepository;
    this.packageService = packageService;
  }

  public Event findBy(Long id) {
    return repository.findById(id).orElseThrow(EventNotFoundException::new);
  }

  public List<Event> findAllEvents() {
    return this.repository.findAllByOrderByDateDesc();
  }

  public EventDetails findByEventUrl(String eventUrl) {
    var event = repository.findEventDetailsByEventUrl(eventUrl).orElseThrow(EventNotFoundException::new);
    event.setImagePath(this.photoRepository.findPhotoByEventId(event.getId()));
    return event;
  }

  @Transactional
  public void save(CreateEventRequest request) {
    var eventTitle = request.title();
    var uploadedCover = bucketService.upload(eventTitle, request.cover());
    var pastEventsUrls = bucketService.upload(eventTitle, request.pastEvents());
    var eventUrl = eventTitle.toLowerCase().replace(" ", "-");
    var event = new Event(
        eventTitle,
        request.description(),
        request.location(),
        request.date(),
        uploadedCover.path(),
        null,
        eventUrl,
        request.details());
    AggregateReference<Event, Long> eventSaved = AggregateReference.to(this.repository.save(event).id());
    this.socialMediaRepository.save(
        new SocialMedia(eventSaved, request.instagram(), request.whatsapp()));
    var photos = convertIntoPhotos(eventSaved, pastEventsUrls);
    this.photoRepository.saveAll(photos);
    this.packageService.save(request.toPackages(eventSaved));
  }

  private List<Photo> convertIntoPhotos(
      AggregateReference<Event, Long> event, List<BucketImage> images) {
    return images.stream().map(image -> new Photo(event, image.path())).toList();
  }

  @Transactional
  public void delete(String eventUrl) {
    var event = findByEventUrl(eventUrl);
    this.photoRepository.deleteAllByEventId(event.getId());
    this.socialMediaRepository.deleteSocialMediaByEventId(event.getId());
    this.repository.deleteById(event.getId());
    // delete folder from s3
    this.bucketService.deleteFolder(event.title());
  }

  public org.springframework.data.domain.Page<Event> findAll(Page page) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getName().equals("admin")) {
      return this.repository.findAllByOrderByDateDesc(page.toPageRequest());
    }
    return this.repository.findAllByDateIsGreaterThanEqualOrderByDate(
        LocalDate.now(), page.toPageRequest());
  }

  public List<Event> findAllActiveEvents() {
    return this.repository.findAllActiveEventsByDateIsAfter(LocalDate.now());
  }

  public SubEventWithEvent getEventAlbumsBy(String eventUrl) {
    var event = this.repository.findByEventUrl(eventUrl).orElseThrow(EventNotFoundException::new);
    var subEvents = this.subEventRepository.findSubEventsBy(eventUrl);

    return new SubEventWithEvent(event, subEvents);
  }

  public org.springframework.data.domain.Page<EventPhotos> getPhotosBy(
      String eventUrl, String albumName, Page page) {
    var subEvent = this.subEventRepository
        .findByName(albumName, eventUrl)
        .orElseThrow(SubEventNotFoundException::new);
    return this.eventPhotosRepository.findEventPhotosBySubEventId(
        subEvent.id(), page.toPageRequest());
  }

  public void removeEventBy(String eventUrl) {
    var subEventIds = this.subEventRepository.findSubEventsBy(eventUrl);
    if (!CollectionUtils.isEmpty(subEventIds)) {
      this.eventPhotosRepository.deleteEventPhotosBy(
          subEventIds.stream().map(SubEvent::id).toList());
      // its avoid subalbums to be deleted and cover albums to be set again
      // this.subEventRepository.deleteAll(subEventIds);
    }
  }

  public void processAlbumBy(String eventUrl) {
    removeEventBy(eventUrl);
    this.repository
        .findByEventUrl(eventUrl)
        .ifPresent(
            event -> {
              var prefix = "events/" + event.eventUrl();
              var foldersPath = this.bucketService.getFoldersNamesBy(prefix);
              foldersPath.forEach(
                  folder -> {
                    var subEventName = getFolderNameFrom(folder, prefix);
                    // if subevent already exists, then use it or create a new one
                    var subEvent = this.subEventRepository.findByName(subEventName, eventUrl)
                        .orElseGet(() -> {
                          var subEventNew = new SubEvent(AggregateReference.to(event.id()), subEventName);
                          return this.subEventRepository.save(subEventNew);
                        });

                    var fileNamesFromFolder = this.bucketService.getFilesNamesBy(folder);
                    fileNamesFromFolder.forEach(
                        fileName -> this.eventPhotosRepository.save(
                            new EventPhotos(
                                AggregateReference.to(subEvent.id()),
                                bucketService.getBucketUri() + fileName)));
                  });
            });
  }

  private String getFolderNameFrom(String folderPath, String prefix) {
    return folderPath.substring(prefix.length()).replace("/", "");
  }

  public long count() {
    return this.repository.count();
  }

  public void update(Long eventId, UpdateEventRequest request) {
    var event = this.repository.findById(eventId).orElseThrow(EventNotFoundException::new);
    var packRequest = request.toPackages(AggregateReference.to(event.id()));
    var packages = event.packages();
    var newPackages = packages.stream().filter(Predicate.not(packRequest::contains)).toList();
    if (!newPackages.isEmpty()) {
      this.packageService.save(newPackages);
    }
  }

  public void setFeaturedEvent(Long eventId) {
    this.repository.unSetAllFeatureEvent();
    this.repository.setFeatureEvent(eventId);
  }

  public void setUnFeaturedEvent() {
    this.repository.unSetAllFeatureEvent();
  }

  public Optional<EventDetails> getFeaturedEvent() {
    return this.repository.findFeatureEvent();
  }

  public List<Event> getNotFeaturedAndIncomingEvents() {
    return this.repository.findAllByFeatureEventIsFalseAndDateIsGreaterThanEqualOrderByDate(LocalDate.now());
  }
}
