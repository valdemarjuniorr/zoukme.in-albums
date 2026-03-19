package in.zoukme.zouk_album.repositories.photos;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.PhotoLike;

public interface PhotoLikeRepository extends ListCrudRepository<PhotoLike, Long> {

  Optional<PhotoLike> findByUserIdAndEventPhotoId(Long userId, Long photoId);

  @Modifying
  @Query("DELETE FROM photo_likes where user_id= :userId AND event_photo_id= :eventPhotoId")
  void delete(Long userId, Long eventPhotoId);

  Long countByEventPhotoId(Long eventPhotoId);

}
