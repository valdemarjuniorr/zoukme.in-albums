package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Album;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {

  List<Album> findAllByOrderByEventDateDesc();

  void deleteAlbumByEventId(Long eventId);

  Optional<Album> findAlbumByEventId(Long eventId);
}
