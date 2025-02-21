package in.zoukme.zouk_album.domains;

import java.time.LocalDate;
import java.util.Set;

import in.zoukme.zouk_album.utils.DateUtils;
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
    String details) {

  public Boolean isComingUp() {
    var now = LocalDate.now();
    return date.isEqual(now) || date.isAfter(now);
  }

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(this.date);
  }
}
