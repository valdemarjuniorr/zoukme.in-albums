package in.zoukme.zouk_album.repositories.payments;

import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.domains.payments.Payment;
import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface PaymentsRepository
    extends ListPagingAndSortingRepository<Payment, Long>, ListCrudRepository<Payment, Long> {

  @Query("SELECT * FROM payments WHERE package_id = :packageId")
  Optional<Payment> findByPackageId(Long packageId);

  @Modifying
  @Query("UPDATE payments SET status = :status, payment_date = NOW() WHERE id = :id")
  void updateStatus(Long id, PaymentStatus status);

  @Query(
      """
          select pay.id, ev.title as event_title, ev.location as event_location, ev.date as event_date, ev.cover_url as event_cover_url,
          pack.title as pack_title, pack.description as pack_description, pack.price as pack_price, pay.full_name, pay.amount, pay.email, pay.transaction_id,
          pay.payment_date
          FROM packages pack
                   INNER JOIN events ev ON pack.event_id = ev.id
                   INNER JOIN payments pay ON pay.package_id = pack.id
          where pay.reference_id = :referenceId;
          """)
  Optional<PaymentEmailDetails> findPaymentDetailsByReferenceId(UUID referenceId);

  @Query("SELECT SUM(amount) FROM payments WHERE payment_date > :afterDateTime")
  BigDecimal getTotalAmount(LocalDateTime afterDateTime);

  @Query(
      "SELECT SUM(amount) as sum_price, COUNT(id) as total FROM payments WHERE status = 'PAID' AND payment_date > :afterDateTime")
  SumPriceTotalTransaction getTotalCompleted(LocalDateTime afterDateTime);

  @Query(
      "SELECT SUM(amount) as sum_price, COUNT(id) as total FROM payments WHERE status = 'WAITING' AND payment_date > :afterDateTime")
  SumPriceTotalTransaction getTotalPending(LocalDateTime afterDateTime);

  Page<Payment> findAllByStatus(PaymentStatus status, PageRequest pageRequest);

  Page<Payment> findAllByStatusAndPaymentDateIsBefore(
      PaymentStatus status, LocalDateTime paymentDateBefore, Pageable pageable);

  Page<Payment> findAllByStatusAndPaymentDateIsAfterOrderByPaymentDateDesc(
      PaymentStatus status, LocalDateTime paymentDateAfter, Pageable pageable);

  Page<Payment> findAllByOrderByPaymentDateDesc(Pageable pageable);

  Optional<Payment> findByTransactionId(String transactionId);

  @Modifying
  @Query("UPDATE payments SET status = :status WHERE transaction_id = :transactionId")
  void updatePaymentStatusByReferenceId(String transactionId, PaymentStatus status);
}
