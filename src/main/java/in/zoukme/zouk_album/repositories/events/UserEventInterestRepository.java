package in.zoukme.zouk_album.repositories.events;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.TextValueCount;
import in.zoukme.zouk_album.domains.UserEventInterest;

public interface UserEventInterestRepository extends ListCrudRepository<UserEventInterest, Long> {

  Optional<UserEventInterest> findByUserIdAndEventId(Long userId, Long eventId);

  void deleteByUserIdAndEventId(Long userId, Long eventId);

  @Query("""
          SELECT interest AS text, COUNT(*) AS count
          FROM user_event_interest
          WHERE event_id = :eventId
          GROUP BY interest
      """)
  List<TextValueCount> countInterestsByEventId(Long eventId);

}
