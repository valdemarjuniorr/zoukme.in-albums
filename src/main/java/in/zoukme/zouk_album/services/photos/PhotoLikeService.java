package in.zoukme.zouk_album.services.photos;

import in.zoukme.zouk_album.domains.PhotoLike;
import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.repositories.photos.PhotoLikeRepository;
import in.zoukme.zouk_album.services.users.UserService;
import org.springframework.stereotype.Service;

@Service
public class PhotoLikeService {

  private final PhotoLikeRepository repository;
  private final UserService userService;

  public PhotoLikeService(PhotoLikeRepository repository, UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  public Long like(Long eventPhotoId) {
    var user = getUserLogged();

    repository.save(new PhotoLike(eventPhotoId, user.id()));
    var count = repository.countByEventPhotoId(eventPhotoId);
    return count > 0 ? count : 0;
  }

  public Long dislike(Long eventPhotoId) {
    var user = getUserLogged();
    repository.delete(user.id(), eventPhotoId);

    var count = repository.countByEventPhotoId(eventPhotoId);
    return count > 0 ? count : 0;
  }

  private User getUserLogged() {
    return userService
        .getUserLogged()
        .orElseThrow(() -> new RuntimeException("User not logged in"));
  }
}
