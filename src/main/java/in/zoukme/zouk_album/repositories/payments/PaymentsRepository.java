package in.zoukme.zouk_album.repositories.payments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.domains.payments.Payment;
import in.zoukme.zouk_album.domains.payments.PaymentStatus;

public interface PaymentsRepository
    extends ListPagingAndSortingRepository<Payment, Long>, ListCrudRepository<Payment, Long> {

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
      where pay.reference_id = :referenceId
      """)
  Optional<PaymentEmailDetails> findPaymentDetailsByReferenceId(UUID referenceId);

  @Query("""
          SELECT SUM(pay.amount) as sum_price, COUNT(pay.id) as total FROM packages pack
           inner join payments pay on pack.id = pay.package_id
          WHERE pack.event_id = :eventId  and status = 'EXPIRED'
      """)
  SumPriceTotalTransaction getTotalExpired(Long eventId);

  @Query("""
          SELECT SUM(pay.amount) as sum_price, COUNT(pay.id) as total FROM packages pack
           inner join payments pay on pack.id = pay.package_id
          WHERE pack.event_id = :eventId  and status = 'PAID'
      """)
  SumPriceTotalTransaction getTotalCompleted(Long eventId);

  @Query("""
          SELECT SUM(pay.amount) as sum_price, COUNT(pay.id) as total FROM packages pack
           inner join payments pay on pack.id = pay.package_id
          WHERE pack.event_id = :eventId  and status = 'WAITING'
      """)
  SumPriceTotalTransaction getTotalPending(Long eventId);

  Page<Payment> findAllByStatus(PaymentStatus status, PageRequest pageRequest);

  Page<Payment> findAllByStatusAndPaymentDateIsBefore(
      PaymentStatus status, LocalDateTime paymentDateBefore, Pageable pageable);

  Page<Payment> findAllByStatusOrderByPaymentDateDesc(PaymentStatus status, Pageable pageable);

  Page<Payment> findAllByOrderByPaymentDateDesc(Pageable pageable);

  Optional<Payment> findByTransactionId(String transactionId);

  @Modifying
  @Query("UPDATE payments SET status = :status WHERE transaction_id = :transactionId")
  void updatePaymentStatusByReferenceId(String transactionId, PaymentStatus status);

  @Query("""
      select pay.*
      from payments pay
               inner join packages pack on pay.package_id = pack.id
      where pack.event_id = :eventId
      """)
  List<Payment> findAllBy(Long eventId);

  @Query("""
        select * from packages pack
           inner join payments pay on pack.id = pay.package_id
        WHERE pack.event_id = :eventId and status = :status
      """)
  List<Payment> findAllBy(Long eventId, PaymentStatus status);

}
