package in.zoukme.zouk_album.controllers.payments;

import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.services.payments.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping("/pagbank/webhook")
  void redirectFromPagbank(@RequestBody PagBankWebHookResponse response) {
    var status = PaymentStatus.valueOf(response.charges().getFirst().status().name());
    paymentService.updateStatus(response.referenceId(), status);
    log.info("Payment status {} for referenceId: {}", status, response.referenceId());
  }
}
