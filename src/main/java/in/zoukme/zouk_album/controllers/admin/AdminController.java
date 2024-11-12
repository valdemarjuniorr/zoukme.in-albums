package in.zoukme.zouk_album.controllers.admin;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.MetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private Logger log = LoggerFactory.getLogger(AdminController.class);

  private final MetricService service;
  private final AlbumService albumService;

  public AdminController(MetricService service, AlbumService albumService) {
    this.service = service;
    this.albumService = albumService;
  }

  @GetMapping("/albums/create")
  String createAlbum() {
    return "create-album";
  }

  @PostMapping("/albums/create")
  String create(Album album, Model model) {
    this.albumService.save(album);
    var albums = this.albumService.findAll();

    model.addAttribute("albums", albums);

    return "index";
  }

  @GetMapping("/metrics/albums/visits")
  public String visits(Model model) {
    log.info("Showing visits metrics");
    model.addAttribute("visits", this.service.findVisitAlbumMetric());

    return "metric-visiters";
  }
}
