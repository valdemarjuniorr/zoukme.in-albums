package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository
    extends PagingAndSortingRepository<Event, Long>, ListCrudRepository<Event, Long> {

  @Query(
      """
        SELECT e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number, e.cover_url, e.event_url, e.details
        FROM events e
            INNER JOIN social_media sm ON e.id = sm.event_id
        WHERE e.id = :id
    """)
  Optional<EventDetails> findBy(Long id);

  @Query(
      """
    SELECT e.id, e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number, e.cover_url, e.event_url, e.details
    FROM events e
       INNER JOIN social_media sm ON e.id = sm.event_id
    WHERE e.event_url = :eventUrl
    """)
  Optional<EventDetails> findEventDetailsByEventUrl(String eventUrl);

  Optional<Event> findByEventUrl(String eventUrl);

  Page<Event> findAllByOrderByDateDesc(Pageable pageable);

  Page<Event> findAllByDateIsGreaterThanEqualOrderByDate(
      LocalDate dateIsGreaterThan, PageRequest pageRequest);

  List<Event> findAllActiveEventsByDateIsAfter(LocalDate dateAfter);
}
