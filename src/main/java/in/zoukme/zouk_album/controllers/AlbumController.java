package in.zoukme.zouk_album.controllers;

import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.aws.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
class AlbumController {

  private final AlbumService service;
  private final EventService eventService;

  public AlbumController(AlbumService service, EventService eventService) {
    this.service = service;
    this.eventService = eventService;
  }

  @GetMapping
  String findAll(Model model, Authentication authentication) {
    var albums = this.service.findAll(new Page(1, 20));
    model.addAttribute("albums", albums);
    model.addAttribute("authentication", authentication);

    var events = eventService.getNotFeaturedAndIncomingEvents();
    model.addAttribute("events", events);

    return "index";
  }

  @GetMapping("/albums")
  String findAllBy(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "6") Integer size,
      Model model) {
    var pageObj = new Page(page, size);
    var albums = this.service.findAll(pageObj);
    model.addAttribute("albums", albums);
    model.addAttribute("pagination", pageObj.generatePagination(albums.getTotalPages()));

    return "admin/dashboard/albums-table";
  }
}
