package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("photos")
public record Photo(@Id Long id, Long eventId, String imagePath) {}
