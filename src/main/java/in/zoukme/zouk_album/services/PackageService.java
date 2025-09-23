package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.controllers.packages.PersonalDetailsRequest;
import in.zoukme.zouk_album.domains.payments.Package;
import in.zoukme.zouk_album.exceptions.PackageNotFoundException;
import in.zoukme.zouk_album.repositories.PackageRepository;
import in.zoukme.zouk_album.repositories.events.PackageRequest;
import in.zoukme.zouk_album.services.payments.PaymentService;
import java.util.List;
import org.springframework.stereotype.Service;
import in.zoukme.zouk_album.clients.pagbank.PagBankResponse;

@Service
public class PackageService {

  private final PackageRepository repository;
  private final PaymentService paymentService;

  public PackageService(PackageRepository repository, PaymentService paymentService) {
    this.repository = repository;
    this.paymentService = paymentService;
  }

  public void save(List<Package> packages) {
    packages.forEach(this::save);
  }

  public void save(Package pack) {
    this.repository.save(pack);
  }

  public List<Package> findBy(Long eventId) {
    return this.repository.findBy(eventId);
  }

  public Package findById(Long id) {
    return this.repository.findById(id).orElseThrow(PackageNotFoundException::new);
  }

  public PagBankResponse savePendingPayment(PersonalDetailsRequest request) {
    var pack = this.repository.findById(request.packId()).orElseThrow(PackageNotFoundException::new);
    return paymentService.save(request, pack);
  }

  public void update(PackageRequest request) {
    var pack = this.repository.findById(request.id()).orElseThrow(PackageNotFoundException::new);
    var updatedPack = new Package(
        pack.id(), pack.eventId(), request.title(), request.description(), request.price());
    save(updatedPack);
  }

  public void create(Long id, PackageRequest request) {
    this.repository.save(request.toEntity(id));
  }
}
