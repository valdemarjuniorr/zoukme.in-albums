package in.zoukme.zouk_album.repositories.photos;

import in.zoukme.zouk_album.domains.PhotoBookmark;
import java.util.List;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface PhotoBookmarkRepository extends ListCrudRepository<PhotoBookmark, Long> {

  @Modifying
  @Query("INSERT INTO photo_bookmarks (user_id, event_photo_id) VALUES (:userId, :eventPhotoId)")
  boolean bookmark(Long userId, Long eventPhotoId);

  @Modifying
  @Query("DELETE FROM photo_bookmarks WHERE user_id = :userId AND event_photo_id = :eventPhotoId")
  boolean unbookmark(Long userId, Long eventPhotoId);

  @Query(
      """
      SELECT e.id, e.title, e.location, e.date, e.cover_url, e.event_url, COUNT(pb.id) AS count
        FROM photo_bookmarks pb
        INNER JOIN event_photos ep ON ep.id = pb.event_photo_id
        INNER JOIN sub_events se ON se.id = ep.sub_event_id
        INNER JOIN events e ON e.id = se.event_id
      WHERE pb.user_id = :userId
        GROUP BY e.id;
      """)
  List<EventWithBookmarkedPhotosAndCount> findEventsWithPhotosBookmarkedBy(Long userId);

  @Query(
      """
      SELECT ep.id as event_photo_id, e.id as event_id, ep.sub_event_id, ep.image_path FROM photo_bookmarks pb
       INNER JOIN event_photos ep ON ep.id = pb.event_photo_id
        INNER JOIN sub_events se ON se.id = ep.sub_event_id
        INNER JOIN events e ON e.id = se.event_id
      WHERE pb.user_id = :userId
        AND e.id = :eventId
      LIMIT :limit
      OFFSET :offset;
      """)
  List<BookmarkPhotosByEvent> findBookmarkedPhotosByEvent(
      Long eventId, Long userId, Integer limit, Integer offset);

  @Query(
      """
      SELECT COUNT(pb.id) FROM photo_bookmarks pb
       INNER JOIN event_photos ep ON ep.id = pb.event_photo_id
        INNER JOIN sub_events se ON se.id = ep.sub_event_id
        INNER JOIN events e ON e.id = se.event_id
      WHERE pb.user_id = :userId
        AND e.id = :eventId
      """)
  Long countByEventIdAndUserId(Long eventId, Long userId);
}
