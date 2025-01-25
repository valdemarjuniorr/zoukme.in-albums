package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("event_photos")
public record EventPhotos(
    @Id Long id,
    @Column("sub_event_id") AggregateReference<SubEvent, Long> subEventId,
    String imagePath) {

  public EventPhotos(AggregateReference<SubEvent, Long> subEventId, String imagePath) {
    this(null, subEventId, imagePath);
  }
}
