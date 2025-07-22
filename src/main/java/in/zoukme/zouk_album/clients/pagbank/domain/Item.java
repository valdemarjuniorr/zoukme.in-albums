package in.zoukme.zouk_album.clients.pagbank.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record Item(
    @JsonProperty("reference_id") String referenceId,
    String name,
    String description,
    Integer quantity,
    @JsonProperty("unit_amount") Integer unitAmount,
    @JsonProperty("image_url") String imageUrl) {

  public Item(
      String referenceId, String name, String description, BigDecimal price, String imageUrl) {
    this(referenceId, name, description, 1, convertIntoCents(price), imageUrl);
  }

  private static Integer convertIntoCents(BigDecimal price) {
    return price.multiply(BigDecimal.valueOf(100)).intValue();
  }
}
