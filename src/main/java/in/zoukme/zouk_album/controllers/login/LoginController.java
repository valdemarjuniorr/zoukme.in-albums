package in.zoukme.zouk_album.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.services.users.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

  private final UserService service;

  public LoginController(UserService service) {
    this.service = service;
  }

  @GetMapping
  String login(@RequestParam(required = false) Boolean error, Model model) {
    model.addAttribute("error", error);
    return "login";
  }

  @GetMapping("/reset")
  String passwordReset() {
    return "users/password-reset";
  }

  @GetMapping("/redefine")
  String passwordRedefine() {
    return "users/password-redefine";
  }

  @PostMapping("/password-reset")
  String passwordRedefine(@RequestParam String email, Model model) {
    var error = service.resend(email);
    return switch (error) {
      case EMAIL_NOT_FOUND -> "users/email-not-found";

      default -> "users/password-reset";
    };
  }
}
