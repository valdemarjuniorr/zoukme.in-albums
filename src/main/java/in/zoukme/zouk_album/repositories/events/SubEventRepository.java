package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.SubEvent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface SubEventRepository extends ListCrudRepository<SubEvent, Long> {

  @Query(
      """
		SELECT se.id, se.name, se.event_id
	 	FROM sub_events se
			INNER JOIN events e ON se.event_id = e.id
		WHERE e.event_url = :eventUrl
	""")
  List<SubEvent> findSubEventsBy(String eventUrl);

  @Query(
      """
  		SELECT se.id, se.name, se.event_id
  		FROM sub_events se
  			INNER JOIN events e ON se.event_id = e.id
 		WHERE e.event_url = :eventUrl AND se.name = :name
	""")
  Optional<SubEvent> findByName(String name, String eventUrl);
}
