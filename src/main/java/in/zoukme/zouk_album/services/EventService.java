package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.repositories.events.EventDetails;
import in.zoukme.zouk_album.repositories.events.EventWithSocialMedia;
import in.zoukme.zouk_album.domains.SocialMedia;
import in.zoukme.zouk_album.exceptions.EventNotFoundException;
import in.zoukme.zouk_album.repositories.events.EventRepository;
import in.zoukme.zouk_album.repositories.SocialMediaRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

  private final EventRepository repository;
  private final SocialMediaRepository socialMediaRepository;

  public EventService(EventRepository repository, SocialMediaRepository socialMediaRepository) {
    this.repository = repository;
    this.socialMediaRepository = socialMediaRepository;
  }

  public EventDetails findBy(Long id) {
    return repository.findBy(id).orElseThrow(EventNotFoundException::new);
  }

  public Long save(EventWithSocialMedia event) {
    AggregateReference<Event, Long> eventSaved =
        AggregateReference.to(this.repository.save(event.toDomain()).id());
    this.socialMediaRepository.save(
        new SocialMedia(null, eventSaved, event.instagram(), event.phoneNumber()));

    return eventSaved.getId();
  }

  public List<Event> findAll() {
    return this.repository.findAllByOrderByDate();
  }
}
