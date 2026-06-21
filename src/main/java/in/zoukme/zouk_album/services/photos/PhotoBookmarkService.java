package in.zoukme.zouk_album.services.photos;

import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.repositories.photos.BookmarkPhotosByEvent;
import in.zoukme.zouk_album.repositories.photos.EventWithBookmarkedPhotosAndCount;
import in.zoukme.zouk_album.repositories.photos.PhotoBookmarkRepository;
import in.zoukme.zouk_album.services.users.UserService;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class PhotoBookmarkService {

  private final PhotoBookmarkRepository repository;
  private final UserService userService;

  public PhotoBookmarkService(UserService userService, PhotoBookmarkRepository repository) {
    this.userService = userService;
    this.repository = repository;
  }

  public void bookmark(String email, Long eventPhotoId) {
    repository.bookmark(getUser(email).id(), eventPhotoId);
  }

  public void unbookmark(String email, Long eventPhotoId) {
    repository.unbookmark(getUser(email).id(), eventPhotoId);
  }

  public List<EventWithBookmarkedPhotosAndCount> findPhotosWithPhotosBookmarkedBy(String email) {
    return repository.findEventsWithPhotosBookmarkedBy(getUser(email).id());
  }

  public org.springframework.data.domain.Page<BookmarkPhotosByEvent> findBookmarkedPhotosByEvent(
      Long eventId, String username, Page page) {

    var bookmarkedPhotos =
        repository.findBookmarkedPhotosByEvent(
            eventId, getUser(username).id(), page.size(), page.offset());

    return new PageImpl<>(
        bookmarkedPhotos,
        page.toPageRequest(),
        repository.countByEventIdAndUserId(eventId, getUser(username).id()));
  }

  private User getUser(String email) {
    return userService
        .findByUsername(email)
        .orElseThrow(() -> new RuntimeException("User not found or pending: " + email));
  }
}
