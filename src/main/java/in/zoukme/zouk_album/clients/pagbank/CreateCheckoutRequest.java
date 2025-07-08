package in.zoukme.zouk_album.clients.pagbank;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public record CreateCheckoutRequest(
    @JsonProperty("reference_id") UUID referenceId,
    Customer customer,
    List<Item> items,
    List<PaymentMethod> paymentMethods, // TODO: add a type attribute {"type": "credit_card"}
    @JsonProperty("return_url") String returnUrl,
    @JsonProperty("redirect_url") String redirectUrl,
    @JsonProperty("payment_notification_urls")
        List<String> paymentNotificationUrls /* notify payment status */) {

  public CreateCheckoutRequest(
      UUID referenceId,
      Customer customer,
      Item item,
      String returnUrl,
      String redirectUrl,
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
        returnUrl,
        redirectUrl,
        List.of(paymentNotificationUrl)); // paymentNotificationUrls
  }
}
