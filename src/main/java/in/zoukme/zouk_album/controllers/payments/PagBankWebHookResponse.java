package in.zoukme.zouk_album.controllers.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

record PagBankWebHookResponse(
    String id, @JsonProperty("reference_id") UUID referenceId, List<Change> charges) {
  record Change(
      String id,
      PagBankStatus status,
      @JsonProperty("reference_id") String referenceId,
      @JsonProperty("paid_at") String paidAt,
      @JsonProperty("payment_method") PaymentMethod paymentMethod) {
    record PaymentMethod(String type, Card card, Holder holder) {
      record Holder(String name, @JsonProperty("tax_id") String taxId) {}

      private record Card(@JsonProperty("last_digits") String lastDigits) {}
    }
  }
}
