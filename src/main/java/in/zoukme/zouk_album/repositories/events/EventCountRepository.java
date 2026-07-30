package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.EventCount;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventCountRepository extends CrudRepository<EventCount, Long> {

  Optional<EventCount> findByEventId(Long eventId);

  @Modifying
  @Query(
      """
      UPDATE event_count
      SET count = count + 1
      WHERE event_id = :eventId
      """)
  void incrementCountByEventId(Long eventId);
}
