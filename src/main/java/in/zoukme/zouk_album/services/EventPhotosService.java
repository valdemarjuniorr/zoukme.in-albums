package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.EventPhotos;
import in.zoukme.zouk_album.repositories.events.EventPhotosRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EventPhotosService {

  private final EventPhotosRepository repository;

  public EventPhotosService(EventPhotosRepository repository) {
    this.repository = repository;
  }

  public Optional<EventPhotos> findBy(Long id) {
    return this.repository.findById(id);
  }

  public void deleteBy(Long id) {
    this.repository.deleteById(id);
  }
}
