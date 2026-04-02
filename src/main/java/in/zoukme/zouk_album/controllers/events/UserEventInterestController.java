package in.zoukme.zouk_album.controllers.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.domains.UserEventInterest.Interest;
import in.zoukme.zouk_album.services.UserEventInterestService;
import in.zoukme.zouk_album.services.aws.EventService;

@Controller
@RequestMapping("/events")
public class UserEventInterestController {

  private final Logger log = LoggerFactory.getLogger(UserEventInterestController.class);
  private final UserEventInterestService service;
  private final EventService eventService;

  public UserEventInterestController(UserEventInterestService service, EventService eventService) {
    this.service = service;
    this.eventService = eventService;
  }

  @PostMapping("/{eventId}/interests/toggle")
  public String toggleInterest(@PathVariable Long eventId, @RequestParam String type, Authentication authentication,
      Model model) {

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

  @GetMapping("/{eventId}/interests/attendees")
  String getAttendees(@PathVariable Long eventId,
      @RequestParam(required = false, defaultValue = "ALL") String filter,
      @RequestHeader(value = "HX-Request", required = false) String hxRequest,
      Model model) {
    log.info("Getting attendees for eventId: {} with filter: {}", eventId, filter);

    var event = eventService.findBy(eventId);
    var attendees = filter.equals("ALL")
        ? service.getAttendees(eventId)
        : service.getAttendeesByInterest(eventId, filter);

    var interestsCount = service.getInterestCounts(eventId);
    long interestedCount = 0;
    long goingCount = 0;

    for (var interestCount : interestsCount) {
      switch (interestCount.text().toUpperCase()) {
        case "INTERESTED" -> interestedCount = interestCount.count();
        case "GOING" -> goingCount = interestCount.count();
      }
    }

    var totalCount = interestedCount + goingCount;

    model.addAttribute("eventId", eventId);
    model.addAttribute("eventUrl", event.eventUrl());
    model.addAttribute("eventTitle", event.title());
    model.addAttribute("attendees", attendees);
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("interestedCount", interestedCount);
    model.addAttribute("goingCount", goingCount);
    model.addAttribute("currentFilter", filter);

    if ("true".equals(hxRequest)) {
      return "events/attendees :: filter-and-grid";
    }

    return "events/attendees";
  }

}
