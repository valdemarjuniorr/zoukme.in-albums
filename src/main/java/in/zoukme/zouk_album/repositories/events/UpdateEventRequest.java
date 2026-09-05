package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.Photo;
import in.zoukme.zouk_album.domains.payments.Package;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @RequestParam("pastEvents") List<MultipartFile> newPastEvents,
    List<String> pastEvents) {

  public UpdateEventRequest(Event event) {
    this(
        event.id(),
        event.title(),
        event.description(),
        event.date(),
        LocalTime.of(21, 0),
        event.location(),
        Objects.nonNull(event.socialMedia()) ? event.socialMedia().instagram() : null,
        Objects.nonNull(event.socialMedia()) ? event.socialMedia().phoneNumber() : null,
        EventType.CONGRESS,
        EventStatus.CONFIRMED,
        PackageRequest.from(event.packages()),
        event.details(),
        event.coverUrl(),
        null,
        event.photos().stream().map(Photo::imagePath).toList());
  }

  public List<Package> toPackages(AggregateReference<Event, Long> eventSaved) {
    if (Objects.isNull(packages)) {
      return List.of();
    }
    return packages.stream().map(pack -> pack.toDomain(eventSaved)).toList();
  }

  public String whatsapp() {
    if (Objects.nonNull(this.whatsapp)) {
      return this.whatsapp.replaceAll("[^0-9]", "");
    }

    return "";
  }

  /** to remove bug when a file is not uploaded in the interface * */
  @Override
  public List<MultipartFile> newPastEvents() {
    if (Objects.nonNull(newPastEvents)) {
      return newPastEvents.stream()
          .filter(f -> !f.isEmpty() && StringUtils.hasText(f.getOriginalFilename()))
          .toList();
    }
    return null;
  }
}
