package in.zoukme.zouk_album.controllers.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.domains.UserEventInterest.Interest;
import in.zoukme.zouk_album.repositories.users.UserRepository;
import in.zoukme.zouk_album.services.UserEventInterestService;

@Controller
@RequestMapping("/events/interest")
public class UserEventInterestController {

  private final Logger log = LoggerFactory.getLogger(UserEventInterestController.class);

  private final UserEventInterestService service;

  public UserEventInterestController(UserEventInterestService service, UserRepository userRepository) {
    this.service = service;
  }

  @PostMapping("/{eventId}/toggle")
  public String toggleInterest(@PathVariable Long eventId, @RequestParam String type, Authentication authentication,
      Model model) {

    log.info("Toggling interest for eventId: {}, type: {}, user: {}", eventId, type,
        authentication != null ? authentication.getName() : "anonymous");
    if (authentication == null || !authentication.isAuthenticated()) {
      return "fragments/interest-buttons :: interest-buttons";
    }

    var userName = authentication.getName();

    var interestType = Interest.valueOf(type.toUpperCase());
    service.toggleInterest(userName, eventId, interestType);

    var userInterest = service.getUserInterest(eventId);
    var interestedCount = service.getInterestedCount(eventId);
    var goingCount = service.getGoingCount(eventId);

    model.addAttribute("eventId", eventId);
    model.addAttribute("userInterest", userInterest.orElse(null));
    model.addAttribute("interestedCount", interestedCount);
    model.addAttribute("goingCount", goingCount);

    return "fragments/interest-buttons :: interest-buttons";
  }

}
