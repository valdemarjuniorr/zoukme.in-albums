package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;

public class PaymentWaitingEmail extends AbstractEmailTemplate {

  public PaymentWaitingEmail(PaymentEmailDetails details) {
    super(details);
  }

  @Override
  public String getEmailSubject() {
    return "Zoukme In - Aguardando confirmação de Pagamento";
  }

  @Override
  public String getTemplate() {
    return "/email/payment-waiting";
  }
}
