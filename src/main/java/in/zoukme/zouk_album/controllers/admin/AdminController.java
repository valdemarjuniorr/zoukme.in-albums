package in.zoukme.zouk_album.controllers.admin;

import in.zoukme.zouk_album.controllers.admin.dashboard.DashboardService;
import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.repositories.events.CreateEventRequest;
import in.zoukme.zouk_album.repositories.events.PackageRequest;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.aws.BucketService;
import in.zoukme.zouk_album.services.aws.EventService;
import in.zoukme.zouk_album.services.payments.PaymentService;
import java.time.LocalDate;
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

  private static final Logger log = LoggerFactory.getLogger(AdminController.class);

  private final AlbumService albumService;
  private final EventService eventService;
  private final BucketService bucketService;
  private final DashboardService dashboardService;
  private final PaymentService paymentService;

  public AdminController(
      AlbumService albumService,
      EventService eventService,
      BucketService bucketService,
      DashboardService dashboardService,
      PaymentService paymentService) {
    this.albumService = albumService;
    this.eventService = eventService;
    this.bucketService = bucketService;
    this.dashboardService = dashboardService;
    this.paymentService = paymentService;
  }

  @GetMapping
  String home(Model model, Authentication authentication) {
    model.addAttribute("authentication", authentication);

    return "index";
  }

  @GetMapping("/home")
  String homeAdmin(Model model) {
    var pageObj = new Page(1, 6);
    var albums = this.albumService.findAll(pageObj);
    model.addAttribute("albums", albums);
    model.addAttribute("pagination", pageObj.generatePagination(albums.getTotalPages()));

    return "admin/home";
  }

  @GetMapping("/albums/create")
  String createAlbum() {
    return "admin/albums/create";
  }

  @PostMapping("/albums/create")
  String create(
      String title,
      String description,
      String city,
      LocalDate eventDate,
      @RequestParam("file-upload") MultipartFile cover,
      Model model) {
    this.albumService.save(title, description, city, eventDate, cover);
    var albums = this.albumService.findAll(new Page(1, 6));
    model.addAttribute("albums", albums);

    return "index";
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

  @GetMapping("/events/packages")
  String addPackage() {
    return "admin/events/add_package";
  }

  @PostMapping("/events/packages")
  String addPackage(PackageRequest request, Model model) {
    model.addAttribute("package", request);
    return "admin/events/new-line-package";
  }

  @PostMapping("/events/create")
  String creteEvent(CreateEventRequest request, Model model, Authentication authentication) {
    this.eventService.save(request);
    log.info("Event created: {}", request);

    model.addAttribute("events", this.eventService.findAll());
    model.addAttribute("authentication", authentication);

    return homeAdmin(model);
  }

  @DeleteMapping("/events/{eventUrl}")
  String delete(@PathVariable String eventUrl, Model model, Authentication authentication) {
    this.eventService.delete(eventUrl);
    model.addAttribute("events", this.eventService.findAll());
    model.addAttribute("authentication", authentication);

    model.addAttribute("message", "Album removido com sucesso");

    return "/events/toast";
  }

  @DeleteMapping("/events/{eventUrl}/photos/{fileName}")
  String deletePhoto(
      @PathVariable String fileName,
      @PathVariable String eventUrl,
      Model model,
      Authentication authentication) {
    this.bucketService.deletePhotoBy(eventUrl, fileName);
    model.addAttribute("events", this.eventService.findAll());
    model.addAttribute("authentication", authentication);

    model.addAttribute("message", "Foto removida com sucesso");

    return "/events/toast";
  }

  @PostMapping("/events/{eventUrl}/process")
  String processEvent(@PathVariable String eventUrl, Model model) {
    this.eventService.processAlbumBy(eventUrl);
    model.addAttribute("message", "Album processado com sucesso");

    return "/events/toast";
  }

  @GetMapping("/dashboard/total-albums")
  String getTotalAlbums(Model model) {
    model.addAttribute("total", dashboardService.getTotalAlbums());

    return "admin/dashboard/total_number";
  }

  @GetMapping("/dashboard/total-photos")
  String getTotalPhotos(Model model) {
    model.addAttribute("total", dashboardService.getTotalPhotos());

    return "admin/dashboard/total_number";
  }

  @GetMapping("/dashboard/total-events")
  String getTotalEvents(Model model) {
    model.addAttribute("total", dashboardService.getTotalEvents());

    return "admin/dashboard/total_number";
  }

  @GetMapping("/payments/list")
  String getPaymentsPanel(Model model) {
    model.addAttribute("payments", paymentService.findAll());
    model.addAttribute("totalPending", dashboardService.getTotalPending());
    model.addAttribute("totalCompleted", dashboardService.getTotalCompleted());
    model.addAttribute("total", dashboardService.getTotalAmount());
    return "admin/dashboard/payments/list";
  }
}
