package in.zoukme.zouk_album.services.photos;

import org.springframework.stereotype.Service;

import in.zoukme.zouk_album.repositories.photos.PhotoBookmarkRepository;
import in.zoukme.zouk_album.services.users.UserService;

@Service
public class PhotoBookmarkService {

  private final PhotoBookmarkRepository repository;
  private final UserService userService;

  public PhotoBookmarkService(UserService userService, PhotoBookmarkRepository repository) {
    this.userService = userService;
    this.repository = repository;
  }

  public void bookmark(String email, Long eventPhotoId) {
    var user = userService.findByUsername(email)
        .orElseThrow(() -> new RuntimeException("User not found or pending: " + email));
    repository.bookmark(user.id(), eventPhotoId);
  }

  public void unbookmark(String email, Long eventPhotoId) {
    var user = userService.findByUsername(email)
        .orElseThrow(() -> new RuntimeException("User not found or pending: " + email));
    repository.unbookmark(user.id(), eventPhotoId);
  }
}
