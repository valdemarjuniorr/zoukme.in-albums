package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("events")
public record Event(
	@Id Long id,
	String title,
	String description,
	String location,
	LocalDate date,
	Long socialMediaId, String coverUrl) {
}
