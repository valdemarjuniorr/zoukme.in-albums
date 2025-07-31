package in.zoukme.zouk_album.controllers.payments;

import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.services.payments.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
class PaymentRestController {

  private static final Logger log = LoggerFactory.getLogger(PaymentRestController.class);
  private final PaymentService paymentService;

  public PaymentRestController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("/pagbank/webhook")
  void redirectFromPagbank(@RequestBody PagBankWebHookResponse response) {
    var status = PaymentStatus.valueOf(response.charges().getFirst().status().name());
    paymentService.updateStatus(response.referenceId(), status);
    log.info("Payment status {} for referenceId: {}", status, response.referenceId());
  }

  @GetMapping("/pagbank/webhook/checkout")
  void updateCheckoutStatus(@RequestBody PagBankCheckoutWebHookResponse response) {
    var status = PaymentStatus.valueOf(response.status());
    paymentService.updateStatus(response.referenceId(), status);
    log.info("Checkout status {} for referenceId: {}", status, response.referenceId());
  }
}
