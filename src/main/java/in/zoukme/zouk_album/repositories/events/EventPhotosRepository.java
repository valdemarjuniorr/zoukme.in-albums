package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.EventPhotos;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface EventPhotosRepository
    extends ListCrudRepository<EventPhotos, Long>,
        ListPagingAndSortingRepository<EventPhotos, Long> {

  Page<EventPhotos> findEventPhotosBySubEventId(Long subEventId, Pageable pageable);

  @Modifying
  @Query("DELETE FROM event_photos WHERE sub_event_id IN(:subEventIds)")
  void deleteEventPhotosBy(List<Long> subEventIds);
}
