package in.zoukme.zouk_album.controllers.payments;

import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.services.payments.PaymentService;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

  private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("/confirmation/{referenceId}/paid")
  String redirectFromPagBank(@PathVariable UUID referenceId, Model model) {
    var details = paymentService.updateStatus(referenceId, PaymentStatus.PAID);
    logger.info("Payment confirmed for referenceId: {}", referenceId);
    model.addAttribute("details", details);

    return "/payments/step3-confirmation";
  }
}
