package in.zoukme.zouk_album.controllers.events;

import in.zoukme.zouk_album.services.EventService;
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
  public String list(Model model) {
    var events = service.findAll();
    model.addAttribute("events", events);

    return "events/list";
  }

  @GetMapping("/{id}")
  public String findBy(@PathVariable Long id, Model model) {
    var event = service.findBy(id);
    model.addAttribute("event", event);

    return "events/details";
  }
}
