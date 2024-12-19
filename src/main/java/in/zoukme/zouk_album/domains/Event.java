package in.zoukme.zouk_album.domains;

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
    @MappedCollection(idColumn = "event_id") Set<Photo> photos, String eventUrl) {

}
