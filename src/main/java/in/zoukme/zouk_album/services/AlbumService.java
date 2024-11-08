package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Counter;
import in.zoukme.zouk_album.exceptions.AlbumNotFoundException;
import in.zoukme.zouk_album.repositories.AlbumRepository;
import java.util.List;

import in.zoukme.zouk_album.repositories.CounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

  private Logger log = LoggerFactory.getLogger(AlbumService.class);

  private final AlbumRepository repository;
  private final CounterRepository counterRepository;

  public AlbumService(AlbumRepository repository, CounterRepository counterRepository) {
    this.repository = repository;
    this.counterRepository = counterRepository;
  }

  public List<Album> findAll() {
    return this.repository.findAllByOrderByEventDateDesc();
  }

  public void save(Album album) {
    var saved = this.repository.save(album);
    counterRepository.save(new Counter(null, 1, saved.id()));
    log.info("Album with id {} and title {} has been saved", saved.id(), saved.title());
  }

  public String visit(Long albumId) {
    incrementVisitBy(albumId);
    var album =
        this.repository.findById(albumId).orElseThrow(() -> new AlbumNotFoundException(albumId));
    return album.url();
  }

  public void incrementVisitBy(Long albumId) {
    var updated = this.counterRepository.increment(albumId);
    if (updated <= 0) {
      throw new AlbumNotFoundException(albumId);
    }
    log.info("Album with id {} has been visited", albumId);
  }
}
