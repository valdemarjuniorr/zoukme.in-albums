package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.exceptions.AlbumNotFoundException;
import in.zoukme.zouk_album.exceptions.EventPhotoNotFoundException;
import in.zoukme.zouk_album.repositories.AlbumRepository;
import in.zoukme.zouk_album.services.aws.BucketImage;
import in.zoukme.zouk_album.services.aws.BucketService;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.EventPhotos;

@Service
public class AlbumService {

  private Logger log = LoggerFactory.getLogger(AlbumService.class);

  private final AlbumRepository repository;
  private final BucketService bucketService;
  private final SubEventService subEventService;
  private final EventPhotosService eventPhotosService;

  public AlbumService(
      AlbumRepository repository,
      BucketService bucketService,
      SubEventService subEventService,
      EventPhotosService eventPhotosService) {
    this.repository = repository;
    this.bucketService = bucketService;
    this.subEventService = subEventService;
    this.eventPhotosService = eventPhotosService;
  }

  public List<Album> findAll() {
    return this.repository.findAllByOrderByEventDateDesc();
  }

  public org.springframework.data.domain.Page<Album> findAll(Page page) {
    return this.repository.findAllByOrderByEventDateDesc(page.toPageRequest());
  }

  public void save(
      Long eventId, String title, String description, String city, LocalDate eventDate, MultipartFile cover) {
    AggregateReference<Event, Long> eventIdAgg = Objects.isNull(eventId) ? null : AggregateReference.to(eventId);
    var uploadedCover = this.bucketService.upload(title, cover);
    var saved = this.repository
        .save(new Album(eventIdAgg, title, city, eventDate, uploadedCover.path()));
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

  public boolean updateAlbumCover(String eventUrl, Long photoId) {
    var eventPhoto = this.eventPhotosService.findBy(photoId).orElseThrow(EventPhotoNotFoundException::new);
    var updatedLines = this.repository.updateCover(eventUrl, eventPhoto.imagePath());
    // TODO: delete photo updated in case in the next-events folder
    return updatedLines == 1;
  }

  public Integer getDistinctCitiesCount() {
    return this.repository.findDistinctCitiesCount();
  }

  public void deletePhotoBy(String eventUrl, Long photoId) {
    var eventPhoto = this.eventPhotosService.findBy(photoId).orElseThrow(EventPhotoNotFoundException::new);
    this.eventPhotosService.deleteBy(photoId);
    this.bucketService.deletePhotoBy(new BucketImage(eventUrl, eventPhoto.imagePath()));
    log.info("Photo with id {} has been deleted from album {}", photoId, eventUrl);
  }
}
