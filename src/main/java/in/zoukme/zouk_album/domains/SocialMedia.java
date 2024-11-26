package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("social_media")
public record SocialMedia(@Id Long id, String instagram, String phoneNumber) {
}
