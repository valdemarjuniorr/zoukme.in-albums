package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;

public class PaymentConfirmationEmail extends AbstractEmailTemplate {

  protected PaymentConfirmationEmail(PaymentEmailDetails details) {
    super(details);
  }

  public String getEmailSubject() {
    return "Zoukme In - Confirmação de Pagamento";
  }

  @Override
  public String getTemplate() {
    return "/email/payment-confirmation";
  }
}
