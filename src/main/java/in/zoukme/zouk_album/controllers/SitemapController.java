package in.zoukme.zouk_album.controllers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.zoukme.zouk_album.services.aws.EventService;

@Controller
@RequestMapping("/sitemap.xml")
public class SitemapController {

  private final EventService eventService;

  public SitemapController(EventService eventService) {
    this.eventService = eventService;
  }

  @Cacheable("sitemap.xml")
  @GetMapping(produces = "application/xml")
  String robots(Model model) {
    var events = eventService.findAllActiveEvents();
    model.addAttribute("events", events);

    return "sitemap.xml";
  }
}
