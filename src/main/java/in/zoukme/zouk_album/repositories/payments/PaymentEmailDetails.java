package in.zoukme.zouk_album.repositories.payments;

import in.zoukme.zouk_album.utils.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PaymentEmailDetails(
    Long id,
    String eventTitle,
    String eventLocation,
    LocalDate eventDate,
    String eventCoverUrl,
    String packTitle,
    String packDescription,
    BigDecimal packPrice,
    String fullName,
    BigDecimal amount,
    String email,
    String transactionId,
    LocalDateTime paymentDate) {

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(eventDate);
  }
  public String getDescriptivePaymentDate() {
    return DateUtils.getDescriptiveDateTime(paymentDate);
  }
}
