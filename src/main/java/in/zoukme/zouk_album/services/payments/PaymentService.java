package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.clients.pagbank.CreateCheckoutRequest;
import in.zoukme.zouk_album.clients.pagbank.PagBankResponse;
import in.zoukme.zouk_album.clients.pagbank.PagBankService;
import in.zoukme.zouk_album.clients.pagbank.domain.Customer;
import in.zoukme.zouk_album.clients.pagbank.domain.Item;
import in.zoukme.zouk_album.controllers.packages.PersonalDetailsRequest;
import in.zoukme.zouk_album.domains.Page;
import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.domains.payments.Package;
import in.zoukme.zouk_album.domains.payments.Payment;
import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.exceptions.EventNotFoundException;
import in.zoukme.zouk_album.exceptions.PaymentNotFoundException;
import in.zoukme.zouk_album.repositories.events.EventRepository;
import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;
import in.zoukme.zouk_album.repositories.payments.PaymentsRepository;
import in.zoukme.zouk_album.services.aws.ses.EmailService;
import in.zoukme.zouk_album.utils.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final String HOST_URL = "https://zoukme.in";
  private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
  private final PaymentsRepository repository;
  private final PagBankService pagBankService;
  private final EmailService emailService;
  private final EventRepository eventRepository;
  private final PaymentsRepository paymentsRepository;

  public PaymentService(
      PaymentsRepository repository,
      PagBankService pagBankService,
      EmailService emailService,
      EventRepository eventRepository,
      PaymentsRepository paymentsRepository) {
    this.repository = repository;
    this.pagBankService = pagBankService;
    this.emailService = emailService;
    this.eventRepository = eventRepository;
    this.paymentsRepository = paymentsRepository;
  }

  public PagBankResponse save(PersonalDetailsRequest payment, Package pack) {
    var event =
        eventRepository.findById(pack.eventId().getId()).orElseThrow(EventNotFoundException::new);
    var customer =
        new Customer(
            payment.firstName() + " " + payment.lastName(),
            payment.email(),
            payment.document(),
            new Customer.Phone(payment.phone()));
    var item =
        new Item(
            payment.packId().toString(),
            pack.title(),
            pack.description(),
            payment.amount(),
            event.coverUrl());
    var referenceId = UUID.randomUUID();
    var request =
        new CreateCheckoutRequest(
            referenceId,
            customer,
            item,
            "%s/events/%s".formatted(HOST_URL, event.eventUrl()),
            "%s/payments/confirmation/%s/paid".formatted(HOST_URL, referenceId),
            HOST_URL + "/payments/pagbank/webhook/checkout",
            HOST_URL + "/payments/pagbank/webhook");

    var response = pagBankService.createCheckOut(request);

    repository.save(
        new Payment(
            AggregateReference.to(pack.id()),
            customer.name(),
            customer.taxId(), // document
            customer.email(),
            payment.phone(),
            payment.amount(),
            referenceId,
            response.id()));
    return response;
  }

  public PaymentEmailDetails updateStatus(UUID referenceId, PaymentStatus status) {
    var payment =
        this.repository
            .findPaymentDetailsByReferenceId(referenceId)
            .orElseThrow(PaymentNotFoundException::new);
    repository.updateStatus(payment.id(), status);
    switch (status) {
      case PAID -> {
        log.info("Payment with ID {} has been paid successfully.", payment.id());
        emailService.send(new PaymentConfirmationEmail(payment));
      }
      case IN_ANALYSIS -> {
        log.info("Payment with ID {} is waiting.", payment.id());
        emailService.send(new PaymentWaitingEmail(payment));
      }
      case DECLINED -> {
        log.info("Payment with ID {} has been declined.", payment.id());
        emailService.send(new PaymentDeclinedEmail(payment));
      }
    }
    return payment;
  }

  public Payment findByPackageId(Long id) {
    return repository.findByPackageId(id).orElseThrow(PaymentNotFoundException::new);
  }

  public Payment findBy(Long id) {
    return repository.findById(id).orElseThrow(PaymentNotFoundException::new);
  }

  public List<Payment> findAll() {
    return repository.findAll();
  }

  public BigDecimal getTotalAmount(LocalDateTime afterDateTime) {
    return repository.getTotalAmount(getDefaultDateTime(afterDateTime));
  }

  public SumPriceTotalTransaction getTotalCompleted(LocalDateTime afterDateTime) {
    return repository.getTotalCompleted(getDefaultDateTime(afterDateTime));
  }

  public SumPriceTotalTransaction getTotalPending(LocalDateTime afterDateTime) {
    return repository.getTotalPending(getDefaultDateTime(afterDateTime));
  }

  /*  * Returns the total number of payments made after a specific date.
   * If no date is provided, it defaults to the beginning of the current year.
   */
  private LocalDateTime getDefaultDateTime(LocalDateTime afterDateTime) {
    return Objects.nonNull(afterDateTime) ? afterDateTime : DateUtils.beginningOfTheCurrentYear();
  }

  public org.springframework.data.domain.Page<Payment> findAllBy(
      PaymentStatus status, LocalDateTime beforeDateTime, Page page) {
    if (Objects.isNull(status)) {
      return repository.findAllByOrderByPaymentDateDesc(page.toPageRequest());
    }
    if (Objects.isNull(beforeDateTime)) {
      beforeDateTime = DateUtils.endDateTime(LocalDate.now().minusYears(1));
    }
    return repository.findAllByStatusAndPaymentDateIsAfterOrderByPaymentDateDesc(
        status, beforeDateTime, page.toPageRequest());
  }

  public void inactivate(String transactionId) {
    var payment =
        paymentsRepository
            .findByTransactionId(transactionId)
            .orElseThrow(PaymentNotFoundException::new);
    pagBankService.inactivateCheckout(transactionId);
    paymentsRepository.updatePaymentStatusByReferenceId(transactionId, PaymentStatus.EXPIRED);
    log.info("Payment with reference ID {} has been inactivated.", transactionId);
  }
}
