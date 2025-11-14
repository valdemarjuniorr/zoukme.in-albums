package in.zoukme.zouk_album.domains;

import in.zoukme.zouk_album.utils.DateUtils;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("albums")
public record Album(
    @Id Long id,
    AggregateReference<Event, Long> eventId,
    String title,
    String city,
    LocalDate eventDate,
    String thumbUrl,
    String url) {

  public Album(AggregateReference<Event, Long> eventId, String title, String city, LocalDate eventDate, String cover) {
    this(
        null,
        eventId,
        title,
        city,
        eventDate,
        cover,
        "/events/%s/albums".formatted(title.toLowerCase().replace(" ", "-")));
  }

  public Album(String title, String city, LocalDate eventDate, String cover) {
    this(
        null,
        title,
        city,
        eventDate, cover);
  }

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(this.eventDate);
  }

  /** If the event date is more than a week away, it is considered new. */
  public Boolean isNew() {
    var oneWeek = LocalDate.now().minusWeeks(1);
    return eventDate.equals(oneWeek) || eventDate.isAfter(oneWeek);
  }
}
