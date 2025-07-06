package in.zoukme.zouk_album.controllers.admin.dashboard;

import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.repositories.photos.PhotoService;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.aws.EventService;
import in.zoukme.zouk_album.services.payments.PaymentService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private final AlbumService albumService;
  private final EventService eventService;
  private final PhotoService photoService;
  private final PaymentService paymentService;

  public DashboardService(
      AlbumService albumService,
      EventService eventService,
      PhotoService photoService,
      PaymentService paymentService) {
    this.albumService = albumService;
    this.eventService = eventService;
    this.photoService = photoService;
    this.paymentService = paymentService;
  }

  public long getTotalEvents() {
    return eventService.count();
  }

  public long getTotalAlbums() {
    return albumService.count();
  }

  public long getTotalPhotos() {
    return photoService.count();
  }

  public BigDecimal getTotalAmount() {
    return paymentService.getTotalAmount();
  }

  public SumPriceTotalTransaction getTotalCompleted() {
    return getValueOrZero(paymentService.getTotalCompleted());
  }

  public SumPriceTotalTransaction getTotalPending() {
    return getValueOrZero(paymentService.getTotalPending());
  }

  private SumPriceTotalTransaction getValueOrZero(SumPriceTotalTransaction total) {
    return total.isZero() ? total.createZeroValues() : total;
  }
}
