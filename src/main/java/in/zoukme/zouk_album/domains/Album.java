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

  public Album(String title, String city, LocalDate eventDate, String cover) {
    this(
        null,
        null,
        title,
        city,
        eventDate,
        cover,
        String.format("/events/%s/albums", title.toLowerCase().replace(" ", "-")));
  }

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(this.eventDate);
  }
}
