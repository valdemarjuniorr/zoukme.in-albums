package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.Photo;
import in.zoukme.zouk_album.domains.SocialMedia;
import in.zoukme.zouk_album.exceptions.EventNotFoundException;
import in.zoukme.zouk_album.repositories.PhotoRepository;
import in.zoukme.zouk_album.repositories.SocialMediaRepository;
import in.zoukme.zouk_album.repositories.events.EventDetails;
import in.zoukme.zouk_album.repositories.events.EventRepository;
import in.zoukme.zouk_album.repositories.events.EventWithSocialMedia;
import in.zoukme.zouk_album.services.aws.BucketService;
import java.util.List;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

  private final EventRepository repository;
  private final SocialMediaRepository socialMediaRepository;
  private final PhotoRepository photoRepository;
  private final BucketService bucketService;

  public EventService(
      EventRepository repository,
      SocialMediaRepository socialMediaRepository,
      PhotoRepository photoRepository,
      BucketService bucketService) {
    this.repository = repository;
    this.socialMediaRepository = socialMediaRepository;
    this.photoRepository = photoRepository;
    this.bucketService = bucketService;
  }

  public EventDetails findBy(Long id) {
    var event = repository.findBy(id).orElseThrow(EventNotFoundException::new);
    event.setImagePath(this.photoRepository.findPhotoByEventId(id));
    return event;
  }

  public EventDetails findByEventUrl(String eventUrl) {
    var event = repository.findEventByEventUrl(eventUrl).orElseThrow(EventNotFoundException::new);
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
}
