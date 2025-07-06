package in.zoukme.zouk_album.controllers.packages;

import in.zoukme.zouk_album.services.PackageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/packages")
public class PackageController {

  private final PackageService service;

  public PackageController(PackageService service) {
    this.service = service;
  }

  @GetMapping
  String step1PersonalDetails(@RequestParam Long packId, Model model) {
    var pack = service.findById(packId);
    model.addAttribute("package", pack);

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
