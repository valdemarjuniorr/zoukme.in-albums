package in.zoukme.zouk_album.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

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
    model.addAttribute("email", email);

    return "users/password-reset";
  }
}
