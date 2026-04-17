package in.zoukme.zouk_album.services.photos;

import java.util.Set;

import org.springframework.stereotype.Service;

import in.zoukme.zouk_album.repositories.PhotoRepository;

@Service
public class PhotoService {

  private final PhotoRepository photoRepository;

  public PhotoService(PhotoRepository photoRepository) {
    this.photoRepository = photoRepository;
  }

  public Set<String> findPhotoByEventId(Long eventId) {
    return photoRepository.findPhotoByEventId(eventId);
  }

  void deleteAllByEventId(Long id) {
    photoRepository.deleteAllByEventId(id);
  }
}
