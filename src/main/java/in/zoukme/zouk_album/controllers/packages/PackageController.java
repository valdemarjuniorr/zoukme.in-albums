package in.zoukme.zouk_album.controllers.packages;

import in.zoukme.zouk_album.services.PackageService;
import in.zoukme.zouk_album.services.users.UserService;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/packages")
public class PackageController {

  private static final Logger log = LoggerFactory.getLogger(PackageController.class);
  private final PackageService service;
  private final UserService userService;

  public PackageController(PackageService service, UserService userService) {
    this.service = service;
    this.userService = userService;
  }

  @GetMapping
  String step1PersonalDetails(@RequestParam Long packId, Authentication authentication, Model model) {
    var pack = service.findById(packId);
    model.addAttribute("package", pack);
    if (Objects.nonNull(authentication)) {
      userService.findProfileByEmail(authentication.getName()).ifPresent(profile -> {
        model.addAttribute("profile", profile);
        model.addAttribute("email", authentication.getName());
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
