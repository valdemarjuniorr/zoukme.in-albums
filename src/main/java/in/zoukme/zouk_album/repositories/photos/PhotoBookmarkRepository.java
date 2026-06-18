package in.zoukme.zouk_album.repositories.photos;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.PhotoBookmark;

public interface PhotoBookmarkRepository extends ListCrudRepository<PhotoBookmark, Long> {

  @Modifying
  @Query("INSERT INTO photo_bookmarks (user_id, event_photo_id) VALUES (:userId, :eventPhotoId)")
  boolean bookmark(Long userId, Long eventPhotoId);

  @Modifying
  @Query("DELETE FROM photo_bookmarks WHERE user_id = :userId AND event_photo_id = :eventPhotoId")
  boolean unbookmark(Long userId, Long eventPhotoId);

}
