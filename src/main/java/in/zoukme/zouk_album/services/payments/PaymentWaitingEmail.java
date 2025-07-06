package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;
import in.zoukme.zouk_album.services.aws.ses.Email;
import in.zoukme.zouk_album.services.aws.ses.EmailTemplate;
import in.zoukme.zouk_album.utils.DateUtils;
import java.util.Map;

public class PaymentWaitingEmail implements EmailTemplate {

  private final PaymentEmailDetails details;

  public PaymentWaitingEmail(PaymentEmailDetails details) {
    this.details = details;
  }

  @Override
  public Email getEmail() {
    return Email.newTemplateEmail(
        details.email(), "Zoukme In - Aguardando confirmação de Pagamento");
  }

  @Override
  public String getTemplateName() {
    return "/email/payment-waiting";
  }

  @Override
  public Map<String, Object> getTemplateVariables() {
    return Map.<String, Object>of(
        "details", details, "descriptiveDate", DateUtils.getDescriptiveDate(details.eventDate()));
  }
}
