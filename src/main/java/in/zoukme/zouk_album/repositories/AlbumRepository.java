package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Album;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {

  List<Album> findAllByOrderByEventDateDesc();
}
