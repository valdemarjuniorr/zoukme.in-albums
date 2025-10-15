package in.zoukme.zouk_album.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import in.zoukme.zouk_album.domains.Album;

public interface AlbumRepository
    extends ListCrudRepository<Album, Long>, ListPagingAndSortingRepository<Album, Long> {

  List<Album> findAllByOrderByEventDateDesc();

  Page<Album> findAllByOrderByEventDateDesc(Pageable pageable);

  void deleteAlbumByEventId(Long eventId);

  Optional<Album> findAlbumByEventId(Long eventId);

  long count();

  @Modifying
  @Query("UPDATE albums SET thumb_url = :coverUrl WHERE url LIKE CONCAT('%', :eventUrl, '%')")
  Integer updateCover(String eventUrl, String coverUrl);

  @Query("SELECT COUNT(DISTINCT city) FROM albums WHERE city IS NOT NULL")
  Integer findDistinctCitiesCount();
}
