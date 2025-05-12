package in.zoukme.zouk_album.services.photos;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photos")
public class PhotoController {

  private static Map<Long, Boolean> database = new HashMap<>();

  @PostMapping("/like/{photoId}")
  String like(@PathVariable Long photoId, Model model) {
    model.addAttribute("social", findById(photoId) ? new Like() : new Unlike());

    return "photos/liked";
  }

  @PostMapping("/bookmark/{photoId}")
  String bookmark(Long photoId, Model model) {

    model.addAttribute("social", findById(photoId) ? new Bookmark() : new Unbookmark());
    return "photos/bookmarked";
  }

  private Boolean findById(Long photoId) {
    var liked = !database.getOrDefault(photoId, false);
    database.put(photoId, liked);
    return liked;
  }
}
