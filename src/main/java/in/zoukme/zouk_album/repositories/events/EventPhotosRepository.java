package in.zoukme.zouk_album.repositories.events;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import in.zoukme.zouk_album.domains.EventPhotos;

public interface EventPhotosRepository extends ListCrudRepository<EventPhotos, Long>,
    ListPagingAndSortingRepository<EventPhotos, Long> {

  Page<EventPhotos> findEventPhotosBySubEventId(Long subEventId, Pageable pageable);

  @Modifying
  @Query("DELETE FROM event_photos WHERE sub_event_id IN(:subEventIds)")
  void deleteEventPhotosBy(List<Long> subEventIds);

  long count();

  @Query("""
      SELECT
          ep.id as event_photo_id,
          ep.image_path,

          (SELECT COUNT(*)
           FROM photo_likes pl2
           WHERE pl2.event_photo_id = ep.id) AS count,

          EXISTS (
              SELECT 1
              FROM photo_likes pl
              WHERE pl.event_photo_id = ep.id
                AND pl.user_id = :userId
          ) AS liked

      FROM event_photos ep

      WHERE ep.sub_event_id = :subEventId

      LIMIT :limit
      OFFSET :offset;
            """)
  List<EventPhotoWithLike> findBy(Long subEventId, Long userId, Integer limit, Integer offset);

  Long countBySubEventId(Long subEventId);
}
