package in.zoukme.zouk_album.domains;

import java.time.LocalDate;

public record EventWithSocialMedia(
	String title,
	String description,
	String location,
	LocalDate date,
	String instagram, String phoneNumber) {
}
