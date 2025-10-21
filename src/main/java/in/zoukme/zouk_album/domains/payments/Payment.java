package in.zoukme.zouk_album.domains.payments;

import in.zoukme.zouk_album.utils.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("payments")
public record Payment(
    @Id Long id,
    AggregateReference<Package, Long> packageId,
    String fullName,
    String document,
    String email,
    String phone,
    BigDecimal amount,
    PaymentStatus status,
    LocalDateTime paymentDate,
    UUID referenceId,
    String transactionId) {

  public Payment(
      AggregateReference<Package, Long> packageId,
      String fullName,
      String document,
      String email,
      String phone,
      BigDecimal amount,
      UUID referenceId,
      String transactionId) {
    this(
        null,
        packageId,
        fullName,
        document,
        email,
        phone,
        amount,
        PaymentStatus.WAITING,
        LocalDateTime.now(),
        referenceId,
        transactionId);
  }

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(this.paymentDate.toLocalDate());
  }

  public Boolean isStatusPaid() {
    return PaymentStatus.PAID.equals(this.status);
  }

  public String getShortTransactionId() {
    return transactionId.substring(transactionId.length() - 6);
  }
}
