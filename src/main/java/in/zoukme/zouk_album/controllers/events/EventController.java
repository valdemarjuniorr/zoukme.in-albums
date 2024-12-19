package in.zoukme.zouk_album.controllers.events;

import in.zoukme.zouk_album.services.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {

  private final EventService service;

  public EventController(EventService service) {
    this.service = service;
  }

  @GetMapping
  public String list(Model model, Authentication authentication) {
    var events = service.findAll();
    model.addAttribute("events", events);
    model.addAttribute("authentication", authentication);

    return "events/list";
  }

  @GetMapping("/{title}")
  public String findByEventUrl(@PathVariable String title, Model model) {
    var event = service.findByEventUrl(title);
    model.addAttribute("event", event);

    return "events/details";
  }
}
