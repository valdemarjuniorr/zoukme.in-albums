package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;
import in.zoukme.zouk_album.services.aws.ses.Email;
import in.zoukme.zouk_album.services.aws.ses.EmailTemplate;
import in.zoukme.zouk_album.utils.DateUtils;
import java.util.Map;

abstract class AbstractEmailTemplate implements EmailTemplate {

  private final PaymentEmailDetails details;

  protected AbstractEmailTemplate(PaymentEmailDetails details) {
    this.details = details;
  }

  @Override
  public Email getEmail() {
    return new Email(details.email(), "contato@zoukme.in", getEmailSubject(), null);
  }

  @Override
  public String getTemplateName() {
    return getTemplate();
  }

  @Override
  public Map<String, Object> getTemplateVariables() {
    return Map.of(
        "details", details, "descriptiveDate", DateUtils.getDescriptiveDate(details.eventDate()));
  }

  /** Returns the subject of the email. */
  public abstract String getEmailSubject();

  /** Returns the template path for the email. */
  public abstract String getTemplate();
}
