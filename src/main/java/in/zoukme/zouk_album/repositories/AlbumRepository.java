package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.Album;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {

    List<Album> findAllByOrderByEventDateDesc();
}
