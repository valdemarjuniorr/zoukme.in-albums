package in.zoukme.zouk_album.clients.pagbank.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Customer(String name, String email, @JsonProperty("tax_id") String taxId, Phone phone) {

  public record Phone(String country, String area, String number) {
    public Phone(String fullPhone) {
      this("+55", fullPhone.substring(0, 2), fullPhone.substring(2));
    }
  }
}
