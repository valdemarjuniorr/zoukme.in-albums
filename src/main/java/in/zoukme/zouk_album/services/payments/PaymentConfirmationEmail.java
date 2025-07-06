package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;
import in.zoukme.zouk_album.services.aws.ses.Email;
import in.zoukme.zouk_album.services.aws.ses.EmailTemplate;
import in.zoukme.zouk_album.utils.DateUtils;

import java.util.Map;

public class PaymentConfirmationEmail implements EmailTemplate {

  private final PaymentEmailDetails details;

  public PaymentConfirmationEmail(PaymentEmailDetails details) {
    this.details = details;
  }

  @Override
  public Email getEmail() {
    return new Email(
        details.email(), "contato@zoukme.in", "Zoukme In - Confirmação de Pagamento", null);
  }

  @Override
  public String getTemplateName() {
    return "/email/payment-waiting";
  }

  @Override
  public Map<String, Object> getTemplateVariables() {
    return Map.of(
        "details", details, "descriptiveDate", DateUtils.getDescriptiveDate(details.eventDate()));
  }
}
