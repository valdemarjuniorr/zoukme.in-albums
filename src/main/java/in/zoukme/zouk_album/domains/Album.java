package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("albums")
public record Album(
    @Id Long id,
    String title,
    String city,
    LocalDate eventDate,
    String thumbUrl,
    String url) {}
