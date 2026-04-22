package in.zoukme.zouk_album.controllers.admin;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import in.zoukme.zouk_album.domains.Album;
import in.zoukme.zouk_album.domains.Event;

record AlbumUpdateRequest(Long id, Long eventId, String title, String city, LocalDate eventDate) {

  public Album toDomain() {
    AggregateReference<Event, Long> aggregateEventId = Objects.isNull(eventId) ? null : AggregateReference.to(eventId);
    return new Album(id, aggregateEventId, title, city, eventDate, null, null);
  }
}
