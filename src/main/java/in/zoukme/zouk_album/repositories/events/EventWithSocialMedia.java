package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public record EventWithSocialMedia(
    String title,
    String description,
    String location,
    LocalDate date,
    String instagram,
    String phoneNumber,
    String coverUrl,
    Set<String> photos) {

  public Event toDomain() {
    var coverUrl = "";
    if (!CollectionUtils.isEmpty(this.photos)) {
      coverUrl = this.photos.stream().findFirst().get();
      this.photos.remove(coverUrl);
    }
    return new Event(null, this.title, this.description, this.location, this.date, coverUrl, null);
  }
}
