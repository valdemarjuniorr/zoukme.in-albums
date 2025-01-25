package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.EventPhotos;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.domains.Photo;
import in.zoukme.zouk_album.domains.SocialMedia;
import in.zoukme.zouk_album.domains.SubEvent;
import in.zoukme.zouk_album.exceptions.EventNotFoundException;
import in.zoukme.zouk_album.exceptions.SubEventNotFoundException;
import in.zoukme.zouk_album.repositories.PhotoRepository;
import in.zoukme.zouk_album.repositories.SocialMediaRepository;
import in.zoukme.zouk_album.repositories.events.EventDetails;
import in.zoukme.zouk_album.repositories.events.EventPhotosRepository;
import in.zoukme.zouk_album.repositories.events.EventRepository;
import in.zoukme.zouk_album.repositories.events.EventWithSocialMedia;
import in.zoukme.zouk_album.repositories.events.SubEventRepository;
import in.zoukme.zouk_album.services.aws.BucketService;
import java.util.List;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class EventService {

  private final EventRepository repository;
  private final SocialMediaRepository socialMediaRepository;
  private final PhotoRepository photoRepository;
  private final BucketService bucketService;
  private final SubEventRepository subEventRepository;
  private final EventPhotosRepository eventPhotosRepository;

  public EventService(
      EventRepository repository,
      SocialMediaRepository socialMediaRepository,
      PhotoRepository photoRepository,
      BucketService bucketService,
      SubEventRepository subEventRepository,
      EventPhotosRepository eventPhotosRepository) {
    this.repository = repository;
    this.socialMediaRepository = socialMediaRepository;
    this.photoRepository = photoRepository;
    this.bucketService = bucketService;
    this.subEventRepository = subEventRepository;
    this.eventPhotosRepository = eventPhotosRepository;
  }

  public EventDetails findBy(Long id) {
    var event = repository.findBy(id).orElseThrow(EventNotFoundException::new);
    event.setImagePath(this.photoRepository.findPhotoByEventId(id));
    return event;
  }

  public EventDetails findByEventUrl(String eventUrl) {
    var event =
        repository.findEventDetailsByEventUrl(eventUrl).orElseThrow(EventNotFoundException::new);
    event.setImagePath(this.photoRepository.findPhotoByEventId(event.getId()));
    return event;
  }

  @Transactional
  public Long save(EventWithSocialMedia event) {
    AggregateReference<Event, Long> eventSaved =
        AggregateReference.to(this.repository.save(event.toDomain()).id());
    this.socialMediaRepository.save(
        new SocialMedia(null, eventSaved, event.instagram(), event.phoneNumber()));
    event.photos().forEach(photo -> this.photoRepository.save(new Photo(null, eventSaved, photo)));
    return eventSaved.getId();
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

  public List<Event> findAll() {
    return this.repository.findAllByOrderByDate();
  }

  public List<SubEvent> getEventAlbumsBy(String eventUrl) {
    return this.subEventRepository.findSubEventsBy(eventUrl);
  }

  public org.springframework.data.domain.Page<EventPhotos> getPhotosBy(
      String eventUrl, String albumName, Page page) {
    var subEvent =
        this.subEventRepository
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
      this.subEventRepository.deleteAll(subEventIds);
    }
  }

  public void processAlbumBy(String eventUrl) {
    removeEventBy(eventUrl);
    this.repository
        .findByEventUrl(eventUrl)
        .ifPresent(
            event -> {
              var prefix = "events/" + event.eventUrl();
              var folders = this.bucketService.getFoldersNamesBy(prefix);
              folders.forEach(
                  folder -> {
                    var subEvent =
                        new SubEvent(
                            AggregateReference.to(event.id()), getFolderNameFrom(folder, prefix));
                    var fileNamesFromFolder = this.bucketService.getFilesNamesBy(folder);

                    var subEventId = this.subEventRepository.save(subEvent).id();
                    fileNamesFromFolder.forEach(
                        fileName ->
                            this.eventPhotosRepository.save(
                                new EventPhotos(
                                    AggregateReference.to(subEventId),
                                    bucketService.getBucketUri() + fileName)));
                  });
            });
  }

  private String getFolderNameFrom(String folderPath, String prefix) {
    return folderPath.substring(prefix.length()).replace("/", "");
  }
}
