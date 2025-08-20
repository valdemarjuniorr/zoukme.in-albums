package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.SubEvent;
import in.zoukme.zouk_album.exceptions.EventPhotoNotFoundException;
import in.zoukme.zouk_album.repositories.events.SubEventRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SubEventService {

  private final SubEventRepository repository;
  private final EventPhotosService eventPhotosService;

  public SubEventService(SubEventRepository repository, EventPhotosService eventPhotosService) {
    this.repository = repository;
    this.eventPhotosService = eventPhotosService;
  }

  public void setCover(Long eventPhotoId) {
    var eventPhoto =
        this.eventPhotosService.findBy(eventPhotoId).orElseThrow(EventPhotoNotFoundException::new);

    this.repository.updateCover(eventPhoto.subEventId().getId(), eventPhoto.imagePath());
  }

  public Optional<SubEvent> findById(Long subEventId) {
    return this.repository.findById(subEventId);
  }
}
