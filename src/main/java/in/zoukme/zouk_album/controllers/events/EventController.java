package in.zoukme.zouk_album.controllers.events;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.services.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {

  private final Logger log = LoggerFactory.getLogger(EventController.class);
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

    return "event-details";
  }

  @PostMapping
  public String create(Event event, Model model) {
    this.service.save(event);
    log.info("Event created: {}", event);

    return "admin/events/create";
  }
}
