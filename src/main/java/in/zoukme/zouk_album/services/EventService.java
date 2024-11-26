package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.EventWithSocialMedia;
import in.zoukme.zouk_album.domains.SocialMedia;
import in.zoukme.zouk_album.exceptions.EventNotFoundException;
import in.zoukme.zouk_album.repositories.EventRepository;
import in.zoukme.zouk_album.repositories.SocialMediaRepository;
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

    public EventWithSocialMedia findBy(Long id) {
        return repository.findBy(id).orElseThrow(EventNotFoundException::new);
    }

    public Long save(Event event) {
		var socialMedia = this.socialMediaRepository.save(event.socialMedia());
		Event with = event.with(socialMedia);
		var eventSaved = this.repository.save(with);
		return eventSaved.id();
    }

    public List<Event> findAll() {
        return null;
    }
}
