package in.zoukme.zouk_album.controllers.admin;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.EventService;
import in.zoukme.zouk_album.services.MetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private Logger log = LoggerFactory.getLogger(AdminController.class);

	private final MetricService service;
	private final AlbumService albumService;
	private final EventService eventService;

	public AdminController(MetricService service, AlbumService albumService, EventService eventService) {
		this.service = service;
		this.albumService = albumService;
		this.eventService = eventService;
	}

	@GetMapping
	public String home() {
		return "admin/home";
	}

	@GetMapping("/albums/create")
	String createAlbum() {
		return "admin/albums/create";
	}

	@PostMapping("/albums/create")
	String create(Album album, Model model) {
		this.albumService.save(album);
		var albums = this.albumService.findAll();

		model.addAttribute("albums", albums);

		return "index";
	}

	@GetMapping("/metrics/albums/visits")
	public String visits(Model model) {
		log.info("Showing visits metrics");
		model.addAttribute("visits", this.service.findVisitAlbumMetric());

		return "admin/metrics/metric-visiters";
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
		model.addAttribute("message", "Album atualizado");

		return albumList(model);
	}

	@GetMapping("/events/create")
	public String createEvent() {
		return "admin/events/create";
	}

	@PostMapping("/events/create")
	public String createEvent(Event event, Model model) {
		Long newEventId = this.eventService.save(event);

		return "redirect:/events/" + newEventId;
	}
}
