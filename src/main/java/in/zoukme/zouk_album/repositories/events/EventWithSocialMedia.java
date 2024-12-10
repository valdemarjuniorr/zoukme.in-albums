package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import java.time.LocalDate;

public record EventWithSocialMedia(
    String title,
    String description,
    String location,
    LocalDate date,
    String instagram,
    String phoneNumber,
    String coverUrl) {

  public Event toDomain() {
    return new Event(null, this.title, this.description, this.location, this.date, coverUrl, null);
  }
}
