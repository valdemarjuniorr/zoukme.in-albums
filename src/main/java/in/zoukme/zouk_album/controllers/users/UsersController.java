package in.zoukme.zouk_album.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

  @GetMapping("/new")
  String newUser() {
    return "users/new-user";
  }

  @PostMapping("/new")
  String newUserPost(CreateUserForm form, Model model) {
    return "users/new-user-response";
  }

  @GetMapping("/check-email")
  String checkEmail() {
    return "users/check-email";
  }

}
