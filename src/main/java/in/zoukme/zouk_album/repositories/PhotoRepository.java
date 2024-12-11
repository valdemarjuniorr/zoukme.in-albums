package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Photo;
import java.util.Set;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface PhotoRepository extends ListCrudRepository<Photo, Long> {

  @Query("SELECT image_path FROM photos WHERE event_id = :eventId")
  Set<String> findPhotoByEventId(Long eventId);
}
