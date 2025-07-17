package in.zoukme.zouk_album.controllers.events;

import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.services.PackageService;
import in.zoukme.zouk_album.services.aws.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/events")
public class EventController {

  private final EventService service;
  private final PackageService packageService;

  public EventController(EventService service, PackageService packageService) {
    this.service = service;
    this.packageService = packageService;
  }

  @GetMapping
  String list(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "6") Integer size,
      Model model,
      Authentication authentication) {
    var events = service.findAll(new Page(page, size));

    model.addAttribute("events", events);
    model.addAttribute("authentication", authentication);

    return "events/list";
  }

  @GetMapping("/{title}")
  String findByEventUrl(@PathVariable String title, Model model, Authentication authentication) {
    var event = service.findByEventUrl(title);

    model.addAttribute("packages", packageService.findBy(event.getId()));
    model.addAttribute("event", event);
    model.addAttribute("authentication", authentication);

    return "events/details";
  }

  @GetMapping("/{eventUrl}/albums")
  String getSubEvents(@PathVariable String eventUrl, Model model) {
    model.addAttribute("event", service.getEventAlbumsBy(eventUrl));

    return "events/subevents/list";
  }

  @GetMapping("/{eventUrl}/albums/{albumName}")
  String getEventPhotosBy(
      @PathVariable String eventUrl,
      @PathVariable String albumName,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "50") Integer size,
      Model model) {
    var pageObj = new Page(page, size);
    var photos = service.getPhotosBy(eventUrl, albumName, pageObj);
    model.addAttribute("photos", photos);
    model.addAttribute("pagination", pageObj.generatePagination(photos.getTotalPages()));
    model.addAttribute("albumName", albumName);
    model.addAttribute("eventUrl", eventUrl);

    return "events/albums/list";
  }
}
