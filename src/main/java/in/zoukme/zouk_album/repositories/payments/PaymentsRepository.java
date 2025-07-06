package in.zoukme.zouk_album.repositories.payments;

import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.domains.payments.Payment;
import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface PaymentsRepository extends ListCrudRepository<Payment, Long> {

  @Query("SELECT * FROM payments WHERE package_id = :packageId")
  Optional<Payment> findByPackageId(Long packageId);

  @Modifying
  @Query("UPDATE payments SET status = :status, payment_date = NOW() WHERE id = :id")
  void updateStatus(Long id, PaymentStatus status);

  @Query("""
          select pay.id, ev.title as event_title, ev.location as event_location, ev.date as event_date, ev.cover_url as event_cover_url, 
          pack.title as pack_title, pack.description as pack_description, pack.price as pack_price, pay.full_name, pay.amount, pay.email, pay.transaction_id,
          pay.payment_date
          FROM packages pack
                   INNER JOIN events ev ON pack.event_id = ev.id
                   INNER JOIN payments pay ON pay.package_id = pack.id
          where pay.transaction_id = :transactionId;
          """)
  Optional<PaymentEmailDetails> findPaymentDetailsByTransactionId(String transactionId);

  @Query("SELECT SUM(amount) FROM payments")
  BigDecimal getTotalAmount();

  @Query(
      "SELECT SUM(amount) as sum_price, COUNT(id) as total FROM payments WHERE status = 'COMPLETED'")
  SumPriceTotalTransaction getTotalCompleted();

  @Query(
      "SELECT SUM(amount) as sum_price, COUNT(id) as total FROM payments WHERE status = 'PENDING'")
  SumPriceTotalTransaction getTotalPending();
}
