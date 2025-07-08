package in.zoukme.zouk_album.controllers.payments;

import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.services.payments.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/confirmation/paid")
  String redirectFromPagBank(@RequestBody PagBankWebHookResponse response, Model model) {
    var details =
        paymentService.updateStatus(
            response.referenceId(),
            PaymentStatus.valueOf(response.charges().getFirst().status().name()));
    model.addAttribute("details", details);

    return "/payments/step3-confirmation";
  }
}
