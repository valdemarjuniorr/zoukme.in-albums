package in.zoukme.zouk_album.controllers.events;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.controllers.meta.MetaTags;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.repositories.users.UserRepository;
import in.zoukme.zouk_album.services.PackageService;
import in.zoukme.zouk_album.services.UserEventInterestService;
import in.zoukme.zouk_album.services.aws.EventService;

@Controller
@RequestMapping("/events")
public class EventController {

  private final EventService service;
  private final PackageService packageService;
  private final UserEventInterestService interestService;
  private final UserRepository userRepository;

  public EventController(EventService service, PackageService packageService,
      UserEventInterestService interestService, UserRepository userRepository) {
    this.service = service;
    this.packageService = packageService;
    this.interestService = interestService;
    this.userRepository = userRepository;
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

    if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
      var user = userRepository.findByEmail(authentication.getName());
      user.ifPresent(u -> {
        var userInterest = interestService.getUserInterest(event.getId());
        model.addAttribute("userInterest", userInterest.orElse(null));
      });
    }

    model.addAttribute("eventId", event.getId());
    model.addAttribute("interestedCount", interestService.getInterestedCount(event.getId()));
    model.addAttribute("goingCount", interestService.getGoingCount(event.getId()));

    return "events/details";
  }

  @GetMapping("/{eventUrl}/albums")
  String getSubEvents(@PathVariable String eventUrl, Model model, Authentication authentication) {
    var event = service.getEventAlbumsBy(eventUrl);
    model.addAttribute("event", event);
    model.addAttribute("authentication", authentication);
    model.addAttribute("seo",
        new MetaTags(event.event().title(), event.event().description(), event.event().coverUrl()));

    return "events/subevents/list";
  }

  @GetMapping("/{eventUrl}/albums/{albumName}")
  String getEventPhotosBy(
      @PathVariable String eventUrl,
      @PathVariable String albumName,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "50") Integer size,
      Model model,
      Authentication authentication) {
    var pageObj = new Page(page, size);

    var photos = service.getPhotosWithLikesBy(eventUrl, albumName, pageObj);
    model.addAttribute("photos", photos);
    model.addAttribute("pagination", pageObj.generatePagination(photos.getTotalPages()));
    model.addAttribute("albumName", albumName);
    model.addAttribute("eventUrl", eventUrl);
    model.addAttribute("authentication", authentication);

    return "events/albums/list";
  }

  @GetMapping("/featured")
  String getFeaturedEvent(Model model) {
    var event = service.getFeaturedEvent();
    event.ifPresent(e -> {
      model.addAttribute("event", e);
      model.addAttribute("hasPackage", service.hasVisiblePackage(e.getId()));
    });

    return "events/feature_event";
  }

}
