package in.zoukme.zouk_album.clients.pagbank;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.zoukme.zouk_album.clients.pagbank.domain.Customer;
import in.zoukme.zouk_album.clients.pagbank.domain.Item;
import in.zoukme.zouk_album.clients.pagbank.domain.PaymentType;
import java.util.List;
import java.util.UUID;

public record CreateCheckoutRequest(
    @JsonProperty("reference_id") UUID referenceId,
    Customer customer,
    List<Item> items,
    @JsonProperty("payment_methods") List<PaymentMethod> paymentMethods,
    @JsonProperty("return_url") String returnUrl,
    @JsonProperty("redirect_url") String redirectUrl,
    @JsonProperty("payment_notification_urls")
        List<String> paymentNotificationUrls /* notify payment status */,
    @JsonProperty("notification_urls") List<String> notificationUrls,
    @JsonProperty("payment_methods_configs") List<PaymentMethodsConfig> paymentMethodConfigs) {

  public CreateCheckoutRequest(
      UUID referenceId,
      Customer customer,
      Item item,
      String returnUrl,
      String redirectUrl,
      String notificationUrl,
      String paymentNotificationUrl) {
    this(
        referenceId,
        customer,
        List.of(item),
        PaymentMethod.getDefaultPaymentTypes(),
        returnUrl,
        redirectUrl,
        List.of(paymentNotificationUrl), // paymentNotificationUrls
        List.of(notificationUrl),
        List.of(PaymentMethodsConfig.installmentLimitThree()));
  }

  record PaymentMethod(PaymentType type) {
    public static List<PaymentMethod> getDefaultPaymentTypes() {
      return List.of(
          new PaymentMethod(PaymentType.PIX),
          new PaymentMethod(PaymentType.CREDIT_CARD),
          new PaymentMethod(PaymentType.BOLETO),
          new PaymentMethod(PaymentType.DEBIT_CARD));
    }
  }
}
