package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("photos")
public record Photo(@Id Long id, AggregateReference<Event, Long> eventId, String imagePath) {

  public Photo(AggregateReference<Event, Long> eventId, String imagePath) {
    this(null, eventId, imagePath);
  }
}
