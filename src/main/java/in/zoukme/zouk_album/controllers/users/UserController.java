package in.zoukme.zouk_album.controllers.users;

import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.exceptions.users.UserPasswordDoesNotMatchException;
import in.zoukme.zouk_album.services.photos.PhotoBookmarkService;
import in.zoukme.zouk_album.services.users.UserService;
import java.util.Objects;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {

  private final UserService service;
  private final PhotoBookmarkService bookmarkService;

  public UserController(PhotoBookmarkService bookmarkService, UserService service) {
    this.bookmarkService = bookmarkService;
    this.service = service;
  }

  @GetMapping("/new")
  String newUser(Model model) {
    model.addAttribute("count", service.count());

    return "users/new-user";
  }

  @PostMapping("/new")
  String create(CreateUserForm request, Model model) {
    service.create(
        String.format("%s %s", request.firstName(), request.lastName()),
        request.phone(),
        request.instagram(),
        request.email(),
        request.password(),
        request.confirmPassword());
    return "users/new-user-response";
  }

  @GetMapping("/check-email")
  String checkEmail() {
    return "users/check-email";
  }

  // After user clicks the link in the email
  @GetMapping("/confirmed")
  String confirmed(@RequestParam("token") String token) {
    var error = service.confirmUserBy(token);
    if (Objects.isNull(error)) {
      return "users/email-confirmed";
    }
    return switch (error) {
      case EXPIRED_TOKEN -> "users/token-expired";
      case EMAIL_NOT_FOUND -> "users/email-not-found";
    };
  }

  @GetMapping("/account")
  String account(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    return "users/account";
  }

  @GetMapping("/account/personal-info")
  String getPersonalInformation(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    var email = userDetails.getUsername();
    model.addAttribute("email", email);
    service.findProfileByEmail(email).ifPresent(u -> model.addAttribute("user", u));

    return "users/account-personal-info";
  }

  @PostMapping("/account")
  String updateAccount(
      @AuthenticationPrincipal UserDetails userDetails, UpdateUserForm request, Model model) {
    service.update(request.toUserProfile(), userDetails.getUsername());
    model.addAttribute("message", "Informações atualizadas!");

    return "/events/toast";
  }

  @PostMapping("/account/password")
  String updatePassword(
      @AuthenticationPrincipal UserDetails userDetails,
      UserPasswordUpdateForm request,
      Model model) {
    if (!request.matches()) {
      throw new UserPasswordDoesNotMatchException();
    }
    service.updatePassword(new User(userDetails.getUsername(), request.newPassword()));
    model.addAttribute("message", "Senha atualizada!");

    return "/events/toast";
  }

  @GetMapping("/bookmarked")
  String getBookMarksPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    var eventsWithBookmarkedPhotos =
        bookmarkService.findPhotosWithPhotosBookmarkedBy(userDetails.getUsername());

    model.addAttribute("bookmarkedEvents", eventsWithBookmarkedPhotos);

    return "users/bookmarked-photos";
  }

  @GetMapping("/events/{eventId}/bookmarked")
  String getBookmkarkedPhotosByEvent(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Long eventId,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "50") Integer size,
      Model model) {
    var pageObj = new Page(page, size);
    var bookmarkedPhotos =
        bookmarkService.findBookmarkedPhotosByEvent(eventId, userDetails.getUsername(), pageObj);

    model.addAttribute("photos", bookmarkedPhotos);
    model.addAttribute("pagination", pageObj.generatePagination(bookmarkedPhotos.getTotalPages()));

    return "/users/bookmarked-event-photo-list";
  }
}
