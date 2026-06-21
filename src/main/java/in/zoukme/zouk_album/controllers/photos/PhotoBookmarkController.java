package in.zoukme.zouk_album.controllers.photos;

import in.zoukme.zouk_album.services.photos.PhotoBookmarkService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photos")
class PhotoBookmarkController {

  private final PhotoBookmarkService service;

  public PhotoBookmarkController(PhotoBookmarkService service) {
    this.service = service;
  }

  @PostMapping("/{eventPhotoId}/bookmark")
  String bookmark(@PathVariable Long eventPhotoId, Authentication authentication, Model model) {
    service.bookmark(authentication.getName(), eventPhotoId);

    model.addAttribute("photoId", eventPhotoId);
    model.addAttribute("bookmarked", Boolean.TRUE);

    return "photos/bookmark";
  }

  @PostMapping("/{eventPhotoId}/unbookmark")
  String unbookmark(@PathVariable Long eventPhotoId, Authentication authentication, Model model) {
    service.unbookmark(authentication.getName(), eventPhotoId);

    model.addAttribute("photoId", eventPhotoId);
    model.addAttribute("bookmarked", Boolean.FALSE);

    return "photos/bookmark";
  }
}
