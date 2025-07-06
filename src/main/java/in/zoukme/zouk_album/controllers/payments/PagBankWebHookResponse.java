package in.zoukme.zouk_album.controllers.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

record PagBankWebHookResponse(
    String id, @JsonProperty("reference_id") String referenceId, List<Change> charges) {
  record Change(
      String id,
      @JsonProperty("reference_id") String referenceId,
      PagBankStatus status,
      @JsonProperty("paid_at") String paidAt,
      @JsonProperty("payment_method") PaymentMethod paymentMethod) {
    record PaymentMethod(String type, Holder holder) {
      record Holder(String name, @JsonProperty("tax_id") String taxId) {}
    }
  }
}
