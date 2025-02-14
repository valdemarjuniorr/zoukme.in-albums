package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Counter;
import in.zoukme.zouk_album.domains.metrics.VisitAlbumMetric;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface CounterRepository extends ListCrudRepository<Counter, Long> {

  @Modifying
  @Query("UPDATE counters c SET count = c.count + 1 WHERE c.album_id = :albumId")
  Integer increment(Long albumId);

  @Query("SELECT * FROM counters c WHERE c.album_id = :albumId")
  Optional<Counter> findByAlbumId(Long albumId);

  @Query(
      """
    SELECT a.title, a.event_date, a.thumb_url, c.count
        FROM albums a
            JOIN counters c ON a.id = c.album_id
    ORDER BY c.count DESC
    """)
  List<VisitAlbumMetric> findVisitAlbumMetrics();

  void deleteByAlbumId(Long id);
}
