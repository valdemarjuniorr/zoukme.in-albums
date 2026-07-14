package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EventRepository
    extends PagingAndSortingRepository<Event, Long>, ListCrudRepository<Event, Long> {

  @Query(
      """
          SELECT e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number, e.cover_url, e.event_url, e.details
          FROM events e
              INNER JOIN social_media sm ON e.id = sm.event_id
          WHERE e.id = :id
      """)
  Optional<EventDetails> findBy(Long id);

  @Query(
      """
          SELECT e.id, e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number, e.cover_url, e.event_url, e.details
          FROM events e
              INNER JOIN social_media sm ON e.id = sm.event_id
          WHERE e.feature_event = true AND date >= CURRENT_DATE
      """)
  Optional<EventDetails> findFeatureEvent();

  @Query(
      """
      SELECT e.id, e.title, e.description, e.location, e.date, sm.instagram, sm.phone_number, e.cover_url, e.event_url, e.details
      FROM events e
         INNER JOIN social_media sm ON e.id = sm.event_id
      WHERE e.event_url = :eventUrl
      """)
  Optional<EventDetails> findEventDetailsByEventUrl(String eventUrl);

  Optional<Event> findByEventUrl(String eventUrl);

  Page<Event> findAllByOrderByDateDesc(Pageable pageable);

  List<Event> findAllByOrderByDateDesc();

  Page<Event> findAllByDateIsGreaterThanEqualOrderByDate(
      LocalDate dateIsGreaterThan, PageRequest pageRequest);

  List<Event> findAllActiveEventsByDateIsAfter(LocalDate dateAfter);

  @Modifying
  @Query(
      """
      UPDATE events
      SET feature_event = TRUE
      WHERE id = :eventId
      """)
  void setFeatureEvent(Long eventId);

  @Modifying
  @Query(
      """
      UPDATE events
      SET feature_event = FALSE
      WHERE feature_event = TRUE
      """)
  void unSetAllFeatureEvent();

  List<Event> findAllByFeatureEventIsFalseAndDateIsGreaterThanEqualOrderByDate(LocalDate now);

  @Query(
      """
        select *
        from events e
        where not exists (select 1 from albums a where a.event_id = e.id)
        ORDER BY date
      """)
  List<Event> findEventsWithoutAlbum();

  @Query(
      """
        select count(ep.id) from event_photos ep
        	inner join sub_events se ON se.id = ep.sub_event_id
        	inner join events e on se.event_id = e.id
        where e.id = :eventId
      """)
  Integer countTotalPhotosBy(Long eventId);

  @Query(
      """
        select count(pl.id) from photo_likes pl
        	inner join event_photos ep ON pl.event_photo_id = ep.id
        	inner join sub_events se on se.id = ep.sub_event_id
        	inner join events e on se.event_id = e.id
        where e.id = :eventId
      """)
  Integer countLikesBy(Long eventId);

  @Query(
      """
        select se.name, ep.image_path, count(pl.event_photo_id) as likes_count from photo_likes pl
         inner join event_photos ep on pl.event_photo_id = ep.id
         inner join sub_events se on ep.sub_event_id = se.id
         inner join events e on se.event_id = e.id
        where e.event_url = :eventUrl
        group by se.name, ep.image_path, pl.event_photo_id
        order by likes_count desc
       limit 3;
      """)
  List<EventFeaturePhotoLike> getMostLikedPhotosBy(String eventUrl);
}
