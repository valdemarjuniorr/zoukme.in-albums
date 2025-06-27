package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Album;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface AlbumRepository
    extends ListCrudRepository<Album, Long>, ListPagingAndSortingRepository<Album, Long> {

  List<Album> findAllByOrderByEventDateDesc();

  Page<Album> findAllByOrderByEventDateDesc(Pageable pageable);

  void deleteAlbumByEventId(Long eventId);

  Optional<Album> findAlbumByEventId(Long eventId);

  long count();
}
