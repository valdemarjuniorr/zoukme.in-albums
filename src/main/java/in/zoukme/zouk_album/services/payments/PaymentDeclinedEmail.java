package in.zoukme.zouk_album.services.payments;


import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;

public class PaymentDeclinedEmail extends AbstractEmailTemplate {

  protected PaymentDeclinedEmail(PaymentEmailDetails details) {
    super(details);
  }

  public String getEmailSubject() {
    return "Zoukme In - Pagamento recusado";
  }

  @Override
  public String getTemplate() {
    return "/email/payment-refused";
  }
}
