package in.zoukme.zouk_album.domains;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

import in.zoukme.zouk_album.domains.users.User;

@Table("photo_likes")
public record PhotoLike(@Id Long id, AggregateReference<Photo, Long> eventPhotoId,
    AggregateReference<User, Long> userId,
    LocalDateTime createdAt) {

  public PhotoLike(Long eventPhotoId, Long userId) {
    this(null, AggregateReference.to(eventPhotoId), AggregateReference.to(userId), LocalDateTime.now());
  }
}
