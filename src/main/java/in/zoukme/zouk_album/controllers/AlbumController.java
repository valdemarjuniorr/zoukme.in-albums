package in.zoukme.zouk_album.controllers;

import in.zoukme.zouk_album.services.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class AlbumController {

  private Logger log = LoggerFactory.getLogger(AlbumController.class);
  private final AlbumService service;

  public AlbumController(AlbumService service) {
    this.service = service;
  }

  @GetMapping
  String findAll(Model model, Authentication authentication) {
    var albums = this.service.findAll();
    model.addAttribute("albums", albums);
    model.addAttribute("authentication", authentication);

    return "index";
  }

  @GetMapping("/{id}/visit")
  String visit(@PathVariable("id") Long albumId) {
    log.info("incrementing album visit by {}", albumId);
    var redirectUrl = this.service.visit(albumId);
    return "redirect:" + redirectUrl;
  }
}
