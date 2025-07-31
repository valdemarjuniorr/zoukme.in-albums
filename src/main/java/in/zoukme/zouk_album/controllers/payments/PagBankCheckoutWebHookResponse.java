package in.zoukme.zouk_album.controllers.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record PagBankCheckoutWebHookResponse(
    String id, @JsonProperty("reference_id") UUID referenceId, String status) {}
