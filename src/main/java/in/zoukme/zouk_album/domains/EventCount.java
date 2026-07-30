package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("event_count")
public record EventCount(@Id Long id, AggregateReference<Event, Long> eventId, Integer count) {

  public EventCount(Long eventId) {
    this(null, AggregateReference.to(eventId), 0);
  }
}
