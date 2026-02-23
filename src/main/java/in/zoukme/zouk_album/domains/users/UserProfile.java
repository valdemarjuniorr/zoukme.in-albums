package in.zoukme.zouk_album.domains.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("users_profile")
public record UserProfile(@Id Long id, String fullName, String phone, String instagram,
    AggregateReference<User, Long> userId) {

  public UserProfile(String fullName, String phone, String instagram, User user) {
    this(null, fullName, phone, instagram, AggregateReference.to(user.id()));
  }
}
