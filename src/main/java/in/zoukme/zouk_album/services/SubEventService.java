package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.SubEvent;
import in.zoukme.zouk_album.exceptions.EventPhotoNotFoundException;
import in.zoukme.zouk_album.repositories.events.EventPhotosRepository;
import in.zoukme.zouk_album.repositories.events.SubEventRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SubEventService {

  private final SubEventRepository repository;
  private final EventPhotosRepository eventPhotosRepository;

  public SubEventService(
      SubEventRepository repository, EventPhotosRepository eventPhotosRepository) {
    this.repository = repository;
    this.eventPhotosRepository = eventPhotosRepository;
  }

  public void setCover(Long eventPhotoId) {
    var eventPhoto =
        this.eventPhotosRepository
            .findById(eventPhotoId)
            .orElseThrow(EventPhotoNotFoundException::new);

    this.repository.updateCover(eventPhoto.subEventId().getId(), eventPhoto.imagePath());
  }

  public Optional<SubEvent> findById(Long subEventId) {
    return this.repository.findById(subEventId);
  }
}
