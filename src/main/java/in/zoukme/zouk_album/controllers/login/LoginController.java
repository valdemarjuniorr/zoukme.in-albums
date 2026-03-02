package in.zoukme.zouk_album.controllers.login;

import java.util.Objects;

import org.springframework.security.core.Authentication;
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
  String login(@RequestParam(required = false) Boolean error, Authentication authentication, Model model) {
    if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
      return "redirect:/";
    }
    model.addAttribute("error", error);
    return "login";
  }

  @GetMapping("/reset")
  String passwordReset() {
    return "users/password-reset";
  }

  @PostMapping("/password-reset")
  String passwordRedefine(@RequestParam String email) {
    var error = service.resend(email);
    if (Objects.isNull(error)) {
      return "users/password-reset";
    }

    return switch (error) {
      case EMAIL_NOT_FOUND -> "users/email-not-found";
      default -> "users/password-reset";
    };
  }

  @GetMapping("/redefine")
  String passwordRedefineValidate(@RequestParam("token") String token, Model model) {
    service.findTokenBy(token);

    return "users/password-redefine";
  }

  @PostMapping("/redefine")
  String passwordRedefine(String token, String password, String confirmPassword,
      Model model) {
    service.redefinePasswordValidate(token, password, confirmPassword);

    return "users/password-updated-success";
  }

}
