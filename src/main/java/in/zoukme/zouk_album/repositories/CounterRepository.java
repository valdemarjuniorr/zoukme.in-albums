package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Counter;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface CounterRepository extends ListCrudRepository<Counter, Long> {

  @Modifying
  @Query("UPDATE counters c SET count = c.count + 1 WHERE c.album_id = :albumId")
  Integer increment(Long albumId);

  @Query("SELECT * FROM counters c WHERE c.album_id = :albumId")
  Optional<Counter> findByAlbumId(Long albumId);
}
