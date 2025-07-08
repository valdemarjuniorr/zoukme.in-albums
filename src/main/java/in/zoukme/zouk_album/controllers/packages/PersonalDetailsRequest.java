package in.zoukme.zouk_album.controllers.packages;

import java.math.BigDecimal;

public record PersonalDetailsRequest(
    Long packId,
    String firstName,
    String lastName,
    String email,
    String phone,
    String document, // CPF
    BigDecimal amount) {

  public PersonalDetailsRequest(
      Long packId,
      String firstName,
      String lastName,
      String email,
      String phone,
      String document, // CPF
      BigDecimal amount) {

    this.packId = packId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = removeFormat(phone);
    this.document = removeFormat(document);
    this.amount = amount;
  }

  private static String removeFormat(String phone) {
    return phone.replaceAll("[^0-9]", "");
  }
}
