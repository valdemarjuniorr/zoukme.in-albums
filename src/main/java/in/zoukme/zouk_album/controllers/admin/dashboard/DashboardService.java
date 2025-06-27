package in.zoukme.zouk_album.controllers.admin.dashboard;

import in.zoukme.zouk_album.repositories.photos.PhotoService;
import in.zoukme.zouk_album.services.AlbumService;
import in.zoukme.zouk_album.services.EventService;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  private final AlbumService albumService;
  private final EventService eventService;
  private final PhotoService photoService;

  public DashboardService(
      AlbumService albumService, EventService eventService, PhotoService photoService) {
    this.albumService = albumService;
    this.eventService = eventService;
    this.photoService = photoService;
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
}
