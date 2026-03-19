package in.zoukme.zouk_album.controllers.photos;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.zoukme.zouk_album.services.photos.PhotoLikeService;

@Controller
@RequestMapping("/photos")
public class PhotoLikeController {

  private final PhotoLikeService service;

  public PhotoLikeController(PhotoLikeService service) {
    this.service = service;
  }

  @PostMapping("/{eventPhotoId}/like")
  String like(@PathVariable Long eventPhotoId, Authentication authentication, Model model) {
    var likesCount = service.like(authentication.getName(), eventPhotoId);

    model.addAttribute("photoId", eventPhotoId);
    model.addAttribute("count", likesCount);
    model.addAttribute("liked", Boolean.TRUE);

    return "photos/like";
  }

  @PostMapping("/{eventPhotoId}/dislike")
  String disLike(@PathVariable Long eventPhotoId, Authentication authentication, Model model) {
    var likesCount = service.dislike(authentication.getName(), eventPhotoId);

    model.addAttribute("photoId", eventPhotoId);
    model.addAttribute("count", likesCount);
    model.addAttribute("liked", Boolean.FALSE);

    return "photos/like";
  }
}
