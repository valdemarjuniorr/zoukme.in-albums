package in.zoukme.zouk_album.controllers.payments;

import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.services.payments.PaymentService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentRestController {

  private final PaymentService paymentService;

  public PaymentRestController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/pagbank/webhook")
  void redirectFromPagbank(@RequestBody PagBankWebHookResponse response, Model model) {
    paymentService.updateStatus(
        response.referenceId(), PaymentStatus.valueOf(response.charges().getFirst().status().name()));
  }

}
