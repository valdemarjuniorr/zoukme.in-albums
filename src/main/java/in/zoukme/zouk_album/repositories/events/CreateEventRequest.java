package in.zoukme.zouk_album.repositories.events;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.payments.Package;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public record CreateEventRequest(
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
    MultipartFile cover,
    @RequestParam("pastEvents") List<MultipartFile> pastEvents) {

  public List<Package> toPackages(AggregateReference<Event, Long> eventSaved) {
    return packages.stream().map(pack -> pack.toDomain(eventSaved)).toList();
  }
}
