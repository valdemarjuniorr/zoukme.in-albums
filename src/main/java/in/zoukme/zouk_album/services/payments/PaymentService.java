package in.zoukme.zouk_album.services.payments;

import in.zoukme.zouk_album.clients.pagbank.CreateCheckoutRequest;
import in.zoukme.zouk_album.clients.pagbank.Customer;
import in.zoukme.zouk_album.clients.pagbank.Item;
import in.zoukme.zouk_album.clients.pagbank.PagBankResponse;
import in.zoukme.zouk_album.clients.pagbank.PagBankService;
import in.zoukme.zouk_album.controllers.packages.PersonalDetailsRequest;
import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.domains.payments.Package;
import in.zoukme.zouk_album.domains.payments.Payment;
import in.zoukme.zouk_album.domains.payments.PaymentStatus;
import in.zoukme.zouk_album.exceptions.PaymentNotFoundException;
import in.zoukme.zouk_album.repositories.payments.PaymentEmailDetails;
import in.zoukme.zouk_album.repositories.payments.PaymentsRepository;
import in.zoukme.zouk_album.services.aws.ses.EmailService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
  private final PaymentsRepository repository;
  private final PagBankService pagBankService;
  private final EmailService emailService;

  public PaymentService(
      PaymentsRepository repository, PagBankService pagBankService, EmailService emailService) {
    this.repository = repository;
    this.pagBankService = pagBankService;
    this.emailService = emailService;
  }

  public PagBankResponse save(PersonalDetailsRequest payment, Package pack) {
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
            "https://www.petz.com.br/blog//wp-content/upload/2018/09/tamanho-de-cachorro-pet-1.jpg");
    var referenceId = UUID.randomUUID();
    var request =
        new CreateCheckoutRequest(
            referenceId,
            customer,
            item,
            "https://zoukme.in",
            "https://zoukme.in/payments/confirmation/paid",
            "https://zoukme.in/payments/pagbank/webhook");

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
      case WAITING -> {
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

  public Payment findById(Long id) {
    return repository.findByPackageId(id).orElseThrow(PaymentNotFoundException::new);
  }

  public List<Payment> findAll() {
    return repository.findAll();
  }

  public BigDecimal getTotalAmount() {
    return repository.getTotalAmount();
  }

  public SumPriceTotalTransaction getTotalCompleted() {
    return repository.getTotalCompleted();
  }

  public SumPriceTotalTransaction getTotalPending() {
    return repository.getTotalPending();
  }
}
