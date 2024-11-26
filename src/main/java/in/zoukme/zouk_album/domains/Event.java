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
	SocialMedia socialMedia) {

	public Event with(SocialMedia socialMedia) {
		return new Event(this.id, this.title, this.description, this.location, this.date, socialMedia);
	}
}
