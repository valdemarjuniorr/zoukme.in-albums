package in.zoukme.zouk_album.controllers;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.services.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class AlbumController {

  private Logger log = LoggerFactory.getLogger(AlbumController.class);
  private final AlbumService service;

  public AlbumController(AlbumService service) {
    this.service = service;
  }

  @GetMapping("")
  String findAll(Model model) {
    var albums = this.service.findAll();
    model.addAttribute("albums", albums);
    return "index";
  }

  @GetMapping("/{id}/visit")
  String visit(@PathVariable("id") Long albumId) {
    log.info("incrementing album visit by {}", albumId);
    var redirectUrl = this.service.visit(albumId);
    return "redirect:" + redirectUrl;
  }

  @GetMapping("/create")
  String create() {
    return "create-album";
  }

  @PostMapping("/create")
  String create(Album album, Model model) {
    this.service.save(album);
    findAll(model);

    return "index :: main";
  }
}
