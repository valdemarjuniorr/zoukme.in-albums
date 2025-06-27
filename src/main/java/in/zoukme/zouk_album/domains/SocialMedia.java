package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("social_media")
public record SocialMedia(
    @Id Long id, AggregateReference<Event, Long> eventId, String instagram, String phoneNumber) {

  public SocialMedia(
      AggregateReference<Event, Long> eventId, String instagram, String phoneNumber) {
    this(null, eventId, instagram, phoneNumber);
  }
}
