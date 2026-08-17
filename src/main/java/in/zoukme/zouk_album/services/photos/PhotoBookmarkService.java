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

  public void bookmark(Long eventPhotoId) {
    repository.bookmark(getUser().id(), eventPhotoId);
  }

  public void unbookmark(Long eventPhotoId) {
    repository.unbookmark(getUser().id(), eventPhotoId);
  }

  public List<EventWithBookmarkedPhotosAndCount> findPhotosWithPhotosBookmarkedBy(String email) {
    return repository.findEventsWithPhotosBookmarkedBy(getUser().id());
  }

  public org.springframework.data.domain.Page<BookmarkPhotosByEvent> findBookmarkedPhotosByEvent(
      Long eventId, String username, Page page) {

    var bookmarkedPhotos =
        repository.findBookmarkedPhotosByEvent(eventId, getUser().id(), page.size(), page.offset());

    return new PageImpl<>(
        bookmarkedPhotos,
        page.toPageRequest(),
        repository.countByEventIdAndUserId(eventId, getUser().id()));
  }

  private User getUser() {
    return userService.getUserLogged().orElseThrow(() -> new RuntimeException("User not found"));
  }
}
