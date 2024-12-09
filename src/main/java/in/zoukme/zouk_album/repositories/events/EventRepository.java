package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends ListCrudRepository<Event, Long> {

  @Query(
      """
		SELECT e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number, e.cover_url, p.*
		FROM events e
			INNER JOIN social_media sm ON e.social_media_id = sm.id
			LEFT JOIN photos p ON e.id = p.event_id
		WHERE e.id = :id
		""")
  Optional<EventWithSocialMedia> findBy(@Param("id") Long id);

  @Query(
      """
		SELECT *
		FROM events e
			INNER JOIN social_media sm ON e.social_media_id = sm.id
			LEFT JOIN photos p ON e.id = p.event_id
		WHERE e.id = :id
		""")
  Optional<Event> findById(@Param("id") Long id);

  List<Event> findAllByOrderByDate();
}
