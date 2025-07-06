package in.zoukme.zouk_album.clients.pagbank;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record CreateCheckoutRequest(
    @JsonProperty("reference_id") String referenceId,
    Customer customer,
    List<Item> items,
    List<PaymentMethod> paymentMethods,
    String redirectUrl,
    List<String> notificationUrls, // notify checkout status
    List<String> paymentNotificationUrls /* notify payment status */) {

  public CreateCheckoutRequest(
      String referenceId,
      Customer customer,
      Item item,
      String redirectUrl,
      String notificationUrl,
      String paymentNotificationUrl) {
    this(
        referenceId,
        customer,
        List.of(item),
        List.of(
            PaymentMethod.CREDIT_CARD,
            PaymentMethod.DEBIT_CARD,
            PaymentMethod.BOLETO,
            PaymentMethod.PIX), /* paymentMethods */
        redirectUrl,
        List.of(notificationUrl), // notificationUrls
        List.of(paymentNotificationUrl)); // paymentNotificationUrls
  }
}
