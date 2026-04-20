package in.zoukme.zouk_album.controllers.admin.dashboard;

import org.springframework.stereotype.Service;

import in.zoukme.zouk_album.domains.SumPriceTotalTransaction;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.aws.EventService;
import in.zoukme.zouk_album.services.payments.PaymentService;
import in.zoukme.zouk_album.services.photos.PhotoService;
import in.zoukme.zouk_album.services.users.UserService;

@Service
public class DashboardService {

  private final AlbumService albumService;
  private final EventService eventService;
  private final PhotoService photoService;
  private final PaymentService paymentService;
  private final UserService userService;

  public DashboardService(
      AlbumService albumService,
      EventService eventService,
      PhotoService photoService,
      PaymentService paymentService, UserService userService) {
    this.albumService = albumService;
    this.eventService = eventService;
    this.photoService = photoService;
    this.paymentService = paymentService;
    this.userService = userService;
  }

  public long getTotalEvents() {
    return eventService.count();
  }

  public long getTotalAlbums() {
    return albumService.count();
  }

  public long getTotalUsers() {
    return userService.count();
  }

  public long getTotalPhotos() {
    return photoService.count();
  }

  public SumPriceTotalTransaction getTotalExpired(Long eventId) {
    return getValueOrZero(paymentService.getTotalExpired(eventId));
  }

  public SumPriceTotalTransaction getTotalCompleted(Long eventId) {
    return getValueOrZero(paymentService.getTotalCompleted(eventId));
  }

  public SumPriceTotalTransaction getTotalPending(Long eventId) {
    return getValueOrZero(paymentService.getTotalPending(eventId));
  }

  public Integer getDistinctCitiesCount() {
    return albumService.getDistinctCitiesCount();
  }

  private SumPriceTotalTransaction getValueOrZero(SumPriceTotalTransaction total) {
    return total.isZero() ? total.createZeroValues() : total;
  }
}
