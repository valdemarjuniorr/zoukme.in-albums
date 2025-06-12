package in.zoukme.zouk_album.controllers.about;

import in.zoukme.zouk_album.services.aws.ses.Email;
import in.zoukme.zouk_album.services.aws.ses.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

  private final EmailService emailService;

  public AboutController(EmailService emailService) {
    this.emailService = emailService;
  }

  @GetMapping
  String about() {
    return "about/about";
  }

  @PostMapping("/contact")
  String sendMessage(Model model, ContactForm contact) {
    emailService.send(
        new Email(
            "valdemarjuniorr@gmail.com",
            "contato@zoukme.in",
            contact.subject(),
            String.format(
                "Nome Completo: %s\nEmail: %s\nCelular: %s\nMensagem: %s",
                contact.fullName(),
                contact.email(),
                contact.mobile(),
                contact.message())));

    return "about/success";
  }
}
