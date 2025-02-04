package in.zoukme.zouk_album.controllers.admin;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.repositories.events.EventWithSocialMedia;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.EventService;
import in.zoukme.zouk_album.services.MetricService;
import in.zoukme.zouk_album.services.aws.BucketService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private Logger log = LoggerFactory.getLogger(AdminController.class);

  private final MetricService service;
  private final AlbumService albumService;
  private final EventService eventService;
  private final BucketService bucketService;

  public AdminController(
      MetricService service,
      AlbumService albumService,
      EventService eventService,
      BucketService bucketService) {
    this.service = service;
    this.albumService = albumService;
    this.eventService = eventService;
    this.bucketService = bucketService;
  }

  @GetMapping
  String home(Model model, Authentication authentication) {
    var albums = this.albumService.findAll();
    model.addAttribute("albums", albums);
    model.addAttribute("authentication", authentication);

    return "index";
  }

  @GetMapping("/home")
  String homeAdmin() {
    return "admin/home";
  }

  @GetMapping("/albums/create")
  String createAlbum() {
    return "admin/albums/create";
  }

  @PostMapping("/albums/create")
  String create(Album album, Model model) {
    this.albumService.save(album);
    var albums = this.albumService.findAll();

    model.addAttribute("albums", albums);

    return "index";
  }

  @GetMapping("/metrics/albums/visits")
  String visits(Model model) {
    log.info("Showing visits metrics");
    model.addAttribute("visits", this.service.findVisitAlbumMetric());

    return "admin/metrics/metric-visiters";
  }

  @GetMapping("/albums")
  String albumList(Model model) {
    model.addAttribute("albums", this.albumService.findAll());

    return "admin/albums/list";
  }

  @GetMapping("/albums/{id}/update")
  String updateAlbum(@PathVariable Long id, Model model) {
    var album = albumService.findBy(id);
    model.addAttribute("album", album);

    return "admin/albums/update";
  }

  @PutMapping("/albums/{id}/update")
  String updateAlbum(Album album, Model model) {
    albumService.update(album);
    var albums = this.albumService.findAll();

    model.addAttribute("albums", albums);
    model.addAttribute("message", "Album atualizado com sucesso");

    return albumList(model);
  }

  @GetMapping("/events/create")
  String createEvent() {
    return "admin/events/create";
  }

  @PostMapping("/events/create")
  String createEvent(EventWithSocialMedia event, Model model, Authentication authentication) {
    this.eventService.save(event);
    log.info("Event created: {}", event);

    model.addAttribute("events", this.eventService.findAll());
    model.addAttribute("authentication", authentication);
    return "/events/list";
  }

  @DeleteMapping("/events/{eventUrl}")
  String delete(@PathVariable String eventUrl, Model model, Authentication authentication) {
    this.eventService.delete(eventUrl);
    model.addAttribute("events", this.eventService.findAll());
    model.addAttribute("authentication", authentication);

    return "/events/list";
  }

  @PostMapping("/events/upload")
  String uploadFiles(
      @RequestParam("files[]") List<MultipartFile> files, String title, Model model) {
    var imagesPath = bucketService.upload(title, files);
    model.addAttribute("images", imagesPath);
    return "/admin/events/images-preview";
  }

  @GetMapping("events/{eventUrl}/process")
  String processEvent(@PathVariable String eventUrl, Model model) {
    model.addAttribute("message", "Album processado");
    this.eventService.processAlbumBy(eventUrl);
    return null;
  }
}
