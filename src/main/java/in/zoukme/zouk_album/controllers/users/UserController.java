package in.zoukme.zouk_album.controllers.users;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.exceptions.users.UserPasswordDoesNotMatchException;
import in.zoukme.zouk_album.services.users.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

  private static final Logger log = LoggerFactory.getLogger(UserController.class);
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping("/new")
  String newUser(Model model) {
    model.addAttribute("count", service.count());

    return "users/new-user";
  }

  @PostMapping("/new")
  String create(CreateUserForm request, Model model) {
    service.create(String.format("%s %s", request.firstName(), request.lastName()), request.phone(),
        request.instagram(), request.email(), request.password(),
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
    var email = userDetails.getUsername();
    model.addAttribute("email", email);
    service.findProfileByEmail(email).ifPresent(u -> model.addAttribute("user", u));

    return "users/account";
  }

  @PostMapping("/account")
  String updateAccount(@AuthenticationPrincipal UserDetails userDetails, UpdateUserForm request, Model model) {
    service.update(request.toUserProfile(), userDetails.getUsername());
    model.addAttribute("message", "Informações atualizadas!");

    return "/events/toast";
  }

  @PostMapping("/account/password")
  String updatePassword(@AuthenticationPrincipal UserDetails userDetails, UserPasswordUpdateForm request, Model model) {
    if (!request.matches()) {
      throw new UserPasswordDoesNotMatchException();
    }
    service.updatePassword(new User(userDetails.getUsername(), request.newPassword()));
    model.addAttribute("message", "Senha atualizada!");

    return "/events/toast";
  }
}
