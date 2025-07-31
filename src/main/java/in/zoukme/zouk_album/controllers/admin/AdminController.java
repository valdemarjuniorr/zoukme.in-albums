package in.zoukme.zouk_album.controllers.admin;

import in.zoukme.zouk_album.controllers.admin.dashboard.DashboardService;
import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.repositories.events.CreateEventRequest;
import in.zoukme.zouk_album.repositories.events.PackageRequest;
import in.zoukme.zouk_album.repositories.events.UpdateEventRequest;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.PackageService;
import in.zoukme.zouk_album.services.aws.BucketService;
import in.zoukme.zouk_album.services.aws.EventService;
import in.zoukme.zouk_album.services.payments.PaymentService;
import in.zoukme.zouk_album.utils.DateUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
  private final PackageService packageService;

  public AdminController(
      AlbumService albumService,
      EventService eventService,
      BucketService bucketService,
      DashboardService dashboardService,
      PaymentService paymentService,
      PackageService packageService) {
    this.albumService = albumService;
    this.eventService = eventService;
    this.bucketService = bucketService;
    this.dashboardService = dashboardService;
    this.paymentService = paymentService;
    this.packageService = packageService;
  }

  @GetMapping
  String home(Model model, Authentication authentication) {
    model.addAttribute("authentication", authentication);

    return "admin/home";
  }

  @GetMapping("/home")
  String homeAdmin(Model model) {
    var pageObj = Page.defaultPage();
    var albums = this.albumService.findAll(pageObj);
    var events = this.eventService.findAll(pageObj);
    model.addAttribute("albums", albums);
    model.addAttribute("events", events);
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
    var albums = this.albumService.findAll(Page.defaultPage());
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
  String createEvent(Model model) {
    model.addAttribute(
        "event",
        new CreateEventRequest(
            null, null, null, null, null, null, null, null, null, List.of(), null, null, null));
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

  @GetMapping("/events/packages/list")
  String getPackages(Model model) {
    model.addAttribute("events", this.eventService.findAllActiveEvents());
    return "admin/dashboard/packages/list";
  }

  @GetMapping("/events/packages/details")
  String getPackagesByEvent(@RequestParam(required = false) Long eventId, Model model) {
    var packages = this.packageService.findBy(eventId);
    model.addAttribute("packages", packages);
    return "admin/dashboard/packages/table";
  }

  @PostMapping("/events/packages/update")
  String updatePackage(PackageRequest request, Model model) {
    this.packageService.update(request);
    model.addAttribute("message", "Pacote atualizado com sucesso");

    return "/events/toast";
  }

  @PostMapping("/events/create")
  String createEvent(CreateEventRequest request, Model model, Authentication authentication) {
    this.eventService.save(request);
    log.info("Event created: {}", request);

    model.addAttribute("events", this.eventService.findAll(Page.defaultPage()));
    model.addAttribute("authentication", authentication);

    model.addAttribute("message", "Album removido com sucesso");

    return "/events/toast";
  }

  @PostMapping("/events/{eventId}/update")
  String updateEvent(@PathVariable Long eventId, UpdateEventRequest request, Model model) {
    this.eventService.update(eventId, request);
    return "";
  }

  @DeleteMapping("/events/{eventUrl}/photos/{fileName}")
  String deletePhoto(
      @PathVariable String fileName,
      @PathVariable String eventUrl,
      Model model,
      Authentication authentication) {
    this.bucketService.deletePhotoBy(eventUrl, fileName);
    model.addAttribute("events", this.eventService.findAll(Page.defaultPage()));
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
  String getPaymentsPanel(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) Integer rangeDays,
      Model model) {
    var paymentStatus = Objects.nonNull(status) ? PaymentStatus.valueOf(status) : null;
    var afterDateTime =
        Objects.nonNull(rangeDays)
            ? DateUtils.endDateTime(LocalDate.now().minusDays(rangeDays))
            : null;
    model.addAttribute(
        "payments", paymentService.findAllBy(paymentStatus, afterDateTime, Page.defaultPage()));
    model.addAttribute("totalPending", dashboardService.getTotalPending(afterDateTime));
    model.addAttribute("totalCompleted", dashboardService.getTotalCompleted(afterDateTime));
    model.addAttribute("total", dashboardService.getTotalAmount(afterDateTime));
    model.addAttribute("paymentsStatus", PaymentStatus.values());

    return "admin/dashboard/payments/list";
  }

  @GetMapping("/events")
  String getEvents(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "6") Integer size,
      Model model,
      Authentication authentication) {
    var pageObj = new Page(page, size);
    var events = this.eventService.findAll(pageObj);
    model.addAttribute("events", events);
    model.addAttribute("authentication", authentication);
    model.addAttribute("pagination", pageObj.generatePagination(events.getTotalPages()));

    return "admin/dashboard/events-table";
  }

  @GetMapping("/events/{id}")
  String getEventDetails(@PathVariable Long id, Model model, Authentication authentication) {
    var event = this.eventService.findBy(id);
    var eventRequest = new UpdateEventRequest(event);
    model.addAttribute("event", eventRequest);
    model.addAttribute("authentication", authentication);

    return "admin/events/update";
  }

  @PostMapping("/subevents/{photoId}/cover")
  String setSubEventCover(@PathVariable Long photoId, Model model) {
    albumService.setCover(photoId);
    model.addAttribute("message", "Capa atualizada com sucesso");

    return "/events/toast";
  }
}
