package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.AttendeeInfo;
import in.zoukme.zouk_album.domains.TextValueCount;
import in.zoukme.zouk_album.domains.UserEventInterest;
import in.zoukme.zouk_album.domains.UserEventInterest.Interest;
import in.zoukme.zouk_album.repositories.events.UserEventInterestRepository;
import in.zoukme.zouk_album.services.users.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEventInterestService {

  private final UserEventInterestRepository repository;
  private final UserService userService;

  public UserEventInterestService(UserEventInterestRepository repository, UserService userService) {
    this.repository = repository;
    this.userService = userService;
  }

  @Transactional
  public UserEventInterest toggleInterest(Long eventId, Interest interest) {
    var user =
        userService.getUserLogged().orElseThrow(() -> new RuntimeException("User not logged in"));
    var userInterest = repository.findByUserIdAndEventId(user.id(), eventId);

    if (userInterest.isPresent()) {
      // this is used when the user clicks the same button again to remove their
      // interest/going status
      repository.deleteByUserIdAndEventId(user.id(), eventId);
      if (userInterest.get().isSame(interest)) {
        return null;
      }
    }
    return repository.save(new UserEventInterest(user.id(), eventId, interest));
  }

  public Optional<UserEventInterest> getUserInterest(Long eventId) {
    var user =
        userService.getUserLogged().orElseThrow(() -> new RuntimeException("User not logged in"));
    return repository.findByUserIdAndEventId(user.id(), eventId);
  }

  public List<TextValueCount> getInterestCounts(Long eventId) {
    return repository.countInterestsByEventId(eventId);
  }

  public List<AttendeeInfo> getAttendees(Long eventId) {
    return repository.findAttendeesByEventId(eventId);
  }

  public List<AttendeeInfo> getAttendeesByInterest(Long eventId, String interest) {
    if (interest.equalsIgnoreCase("ALL")) {
      return getAttendees(eventId);
    }
    return repository.findAttendeesByEventIdAndInterest(eventId, interest);
  }
}
