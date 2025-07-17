package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.Photo;
import in.zoukme.zouk_album.domains.payments.Package;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record UpdateEventRequest(
    Long id,
    String title,
    String description,
    LocalDate date,
    LocalTime time,
    String location,
    String instagram,
    String whatsapp,
    EventType eventType,
    EventStatus eventStatus,
    List<PackageRequest> packages,
    String details,
    String cover,
    List<String> pastEvents) {

  public UpdateEventRequest(Event event) {
    this(
        event.id(),
        event.title(),
        event.description(),
        event.date(),
        LocalTime.of(21, 0),
        event.location(),
        event.socialMedia() != null ? event.socialMedia().instagram() : null,
        event.socialMedia() != null ? event.socialMedia().phoneNumber() : null,
        EventType.CONGRESS,
        EventStatus.CONFIRMED,
        PackageRequest.from(event.packages()),
        event.details(),
        event.coverUrl(),
        event.photos().stream().map(Photo::imagePath).toList());
  }

  public List<Package> toPackages(AggregateReference<Event, Long> eventSaved) {
    return packages.stream().map(pack -> pack.toDomain(eventSaved)).toList();
  }
}
