package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.exceptions.AlbumNotFoundException;
import in.zoukme.zouk_album.repositories.AlbumRepository;
import in.zoukme.zouk_album.services.aws.BucketService;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumService {

  private Logger log = LoggerFactory.getLogger(AlbumService.class);

  private final AlbumRepository repository;
  private final BucketService bucketService;
  private final SubEventService subEventService;

  public AlbumService(
      AlbumRepository repository, BucketService bucketService, SubEventService subEventService) {
    this.repository = repository;
    this.bucketService = bucketService;
    this.subEventService = subEventService;
  }

  public List<Album> findAll() {
    return this.repository.findAllByOrderByEventDateDesc();
  }

  public org.springframework.data.domain.Page<Album> findAll(Page page) {
    return this.repository.findAllByOrderByEventDateDesc(page.toPageRequest());
  }

  public void save(
      String title, String description, String city, LocalDate eventDate, MultipartFile cover) {
    var uploadedCover = this.bucketService.upload(title, cover);
    var saved = this.repository.save(new Album(title, city, eventDate, uploadedCover.path()));
    log.info("Album with id {} and title {} has been saved", saved.id(), saved.title());
  }

  public Album findBy(Long id) {
    return this.repository.findById(id).orElseThrow(() -> new AlbumNotFoundException(id));
  }

  public void update(Album album) {
    var updated = this.repository.save(album);
    log.info("Album with id {} has been updated", updated.id());
  }

  public void deleteBy(Long eventId) {
    this.repository.findAlbumByEventId(eventId).orElseThrow(AlbumNotFoundException::new);
    this.repository.deleteAlbumByEventId(eventId);
    log.info("Album with id {} has been deleted", eventId);
  }

  public long count() {
    return this.repository.count();
  }

  public void setCover(Long photoId) {
    this.subEventService.setCover(photoId);
    log.info("Cover for sub-event with id {} has been set", photoId);
  }
}
