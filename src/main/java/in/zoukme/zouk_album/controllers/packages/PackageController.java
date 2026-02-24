package in.zoukme.zouk_album.controllers.packages;

import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.zoukme.zouk_album.services.PackageService;
import in.zoukme.zouk_album.services.users.UserService;

@Controller
@RequestMapping("/packages")
public class PackageController {

  private final PackageService service;
  private final UserService userService;

  public PackageController(PackageService service, UserService userService) {
    this.service = service;
    this.userService = userService;
  }

  @GetMapping
  String step1PersonalDetails(@RequestParam Long packId, @AuthenticationPrincipal User user, Model model) {
    var pack = service.findById(packId);
    model.addAttribute("package", pack);
    if (Objects.nonNull(user)) {
      userService.findProfileByEmail(user.getUsername()).ifPresent(profile -> {
        model.addAttribute("profile", profile);
        model.addAttribute("email", user.getUsername());
      });
    }

    return "packages/step1-personal-details";
  }

  @PostMapping
  String submitPersonalDetails(PersonalDetailsRequest request, Model model) {
    model.addAttribute("personalDetails", request);
    var pendingPayment = service.savePendingPayment(request);
    model.addAttribute("confirmation", pendingPayment);

    return "redirect:" + pendingPayment.getRedirectUrl();
  }
}
