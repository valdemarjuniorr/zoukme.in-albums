package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.EventWithSocialMedia;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface EventRepository extends ListCrudRepository<Event, Long> {

	@Query("SELECT e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number FROM events e INNER JOIN social_media sm ON e.social_media_id = sm.id WHERE e.id = :id")
	Optional<EventWithSocialMedia> findBy(Long id);

	/**
	 * String title,
	 * 	String description,
	 * 	String location,
	 * 	LocalDate date,
	 * 	String instagram, String phoneNumber
	 */
}
