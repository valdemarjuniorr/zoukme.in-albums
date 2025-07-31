package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("sub_events")
public record SubEvent(
    @Id Long id,
    @Column("event_id") AggregateReference<Event, Long> eventId,
    String name,
    String coverUrl) {

  public SubEvent(AggregateReference<Event, Long> eventId, String name) {
    this(null, eventId, name, null);
  }
}
