package in.zoukme.zouk_album.services.photos;

import in.zoukme.zouk_album.domains.PhotoLike;
import in.zoukme.zouk_album.repositories.photos.PhotoLikeRepository;
import in.zoukme.zouk_album.services.users.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PhotoLikeService {

  private final PhotoLikeRepository repository;
  private final UserService userService;

  public PhotoLikeService(PhotoLikeRepository repository, UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  public Long like(String email, Long eventPhotoId) {
    var user =
        userService
            .findByUsername(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found or pending: " + email));

    repository.save(new PhotoLike(eventPhotoId, user.id()));
    var count = repository.countByEventPhotoId(eventPhotoId);
    return count > 0 ? count : 0;
  }

  public Long dislike(String email, Long eventPhotoId) {
    var user =
        userService
            .findByUsername(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found or pending: " + email));

    repository.delete(user.id(), eventPhotoId);

    var count = repository.countByEventPhotoId(eventPhotoId);
    return count > 0 ? count : 0;
  }
}
