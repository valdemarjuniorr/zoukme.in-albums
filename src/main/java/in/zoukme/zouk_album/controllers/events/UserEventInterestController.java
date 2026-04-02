package in.zoukme.zouk_album.controllers.events;

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

  private final UserEventInterestService service;

  public UserEventInterestController(UserEventInterestService service, UserRepository userRepository) {
    this.service = service;
  }

  @PostMapping("/{eventId}/toggle")
  public String toggleInterest(@PathVariable Long eventId, @RequestParam String type, Authentication authentication,
      Model model) {

    if (authentication == null || !authentication.isAuthenticated()) {
      return "fragments/interest-buttons :: interest-buttons";
    }

    var interestType = Interest.valueOf(type.toUpperCase());
    var userName = authentication.getName();
    service.toggleInterest(userName, eventId, interestType);

    var userInterest = service.getUserInterest(eventId);

    model.addAttribute("eventId", eventId);
    model.addAttribute("userInterest", userInterest.orElse(null));

    var interestsCount = service.getInterestCounts(eventId);
    for (var interestCount : interestsCount) {
      switch (interestCount.text().toUpperCase()) {
        case "INTERESTED" -> model.addAttribute("interestedCount", interestCount.count());
        case "GOING" -> model.addAttribute("goingCount", interestCount.count());
      }
    }

    return "fragments/interest-buttons :: interest-buttons";
  }

}
