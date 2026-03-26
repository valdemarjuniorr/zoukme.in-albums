package in.zoukme.zouk_album.controllers.users;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.services.users.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

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
}
