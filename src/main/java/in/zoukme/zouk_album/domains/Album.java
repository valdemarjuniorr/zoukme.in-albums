package in.zoukme.zouk_album.domains;

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
    String url) {}
