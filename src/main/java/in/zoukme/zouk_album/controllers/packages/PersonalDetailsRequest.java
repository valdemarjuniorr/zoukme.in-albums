package in.zoukme.zouk_album.controllers.packages;

import java.math.BigDecimal;

public record PersonalDetailsRequest(
    Long packId,
    String firstName,
    String lastName,
    String email,
    String phone,
    String document, // CPF
    BigDecimal amount) {}
