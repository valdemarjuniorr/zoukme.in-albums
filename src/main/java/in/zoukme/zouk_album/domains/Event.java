package in.zoukme.zouk_album.domains;

import in.zoukme.zouk_album.domains.payments.Package;
import in.zoukme.zouk_album.utils.DateUtils;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("events")
public record Event(
    @Id Long id,
    String title,
    String description,
    String location,
    LocalDate date,
    String coverUrl,
    @MappedCollection(idColumn = "event_id") Set<Photo> photos,
    String eventUrl,
    String details,
    @MappedCollection(idColumn = "event_id") Set<Package> packages,
    @MappedCollection(idColumn = "event_id") SocialMedia socialMedia) {

  public Event(
      String title,
      String description,
      String location,
      LocalDate date,
      String coverUrl,
      Set<Photo> photos,
      String eventUrl,
      String details) {
    this(null, title, description, location, date, coverUrl, photos, eventUrl, details, null, null);
  }

  public Boolean isComingUp() {
    var now = LocalDate.now();
    return date.isEqual(now) || date.isAfter(now);
  }

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(this.date);
  }
}
