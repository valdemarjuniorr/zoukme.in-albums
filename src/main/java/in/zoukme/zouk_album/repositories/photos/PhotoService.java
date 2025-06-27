package in.zoukme.zouk_album.repositories.photos;

import in.zoukme.zouk_album.repositories.PhotoRepository;
import in.zoukme.zouk_album.repositories.events.EventPhotosRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PhotoService {

  private final PhotoRepository photoRepository;
  private final EventPhotosRepository eventPhotosRepository;

  public PhotoService(
      PhotoRepository photoRepository, EventPhotosRepository eventPhotosRepository) {
    this.photoRepository = photoRepository;
    this.eventPhotosRepository = eventPhotosRepository;
  }

  public long count() {
    return photoRepository.count();
  }

  public Set<String> findPhotoByEventId(Long eventId) {
    return photoRepository.findPhotoByEventId(eventId);
  }

  void deleteAllByEventId(Long id) {
    photoRepository.deleteAllByEventId(id);
  }
}
