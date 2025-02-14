package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Counter;
import in.zoukme.zouk_album.exceptions.AlbumNotFoundException;
import in.zoukme.zouk_album.repositories.AlbumRepository;
import in.zoukme.zouk_album.repositories.CounterRepository;
import java.util.List;
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
    counterRepository.save(new Counter(saved.id()));
    log.info("Album with id {} and title {} has been saved", saved.id(), saved.title());
  }

  public String visit(Long albumId) {
    incrementVisitBy(albumId);
    var album = findBy(albumId);
    return album.url();
  }

  public Album findBy(Long id) {
    return this.repository
        .findById(id)
        .orElseGet(
            () -> {
              throw new AlbumNotFoundException(id);
            });
  }

  public void incrementVisitBy(Long albumId) {
    var updated = this.counterRepository.increment(albumId);
    if (updated <= 0) {
      throw new AlbumNotFoundException(albumId);
    }
    log.info("Album with id {} has been visited", albumId);
  }

  public void update(Album album) {
    var updated = this.repository.save(album);
    log.info("Album with id {} has been updated", updated.id());
  }

  public void deleteBy(Long eventId) {
    var album =
        this.repository.findAlbumByEventId(eventId).orElseThrow(AlbumNotFoundException::new);
    this.counterRepository.deleteByAlbumId(album.id());
    this.repository.deleteAlbumByEventId(eventId);
    log.info("Album with id {} has been deleted", eventId);
  }
}
