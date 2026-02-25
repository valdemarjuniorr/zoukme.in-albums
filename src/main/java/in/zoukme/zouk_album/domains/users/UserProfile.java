package in.zoukme.zouk_album.domains.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("users_profile")
public record UserProfile(@Id Long id, String fullName, String phone, String instagram,
    AggregateReference<User, Long> userId) {

  public UserProfile(String fullName, String phone, String instagram, User user) {
    this(null, fullName, phone.isBlank() ? null : phone.replaceAll("[^0-9]", ""), instagram,
        AggregateReference.to(user.id()));
  }

  public String firstName() {
    return fullName.split(" ")[0];
  }

  public String surname() {
    var parts = fullName.split(" ");
    return parts.length > 1 ? parts[parts.length - 1] : "";
  }
}
