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
import java.util.List;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

  private final EventRepository repository;
  private final SocialMediaRepository socialMediaRepository;
  private final PhotoRepository photoRepository;

  public EventService(
      EventRepository repository,
      SocialMediaRepository socialMediaRepository,
      PhotoRepository photoRepository) {
    this.repository = repository;
    this.socialMediaRepository = socialMediaRepository;
    this.photoRepository = photoRepository;
  }

  public EventDetails findBy(Long id) {
    var event = repository.findBy(id).orElseThrow(EventNotFoundException::new);
    event.setImagePath(this.photoRepository.findPhotoByEventId(id));
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

  public List<Event> findAll() {
    return this.repository.findAllByOrderByDate();
  }
}
