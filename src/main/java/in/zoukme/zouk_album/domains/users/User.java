package in.zoukme.zouk_album.domains.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(
    @Id Long id,
    String email,
    String password,
    String role,
    Boolean enabled,
    String oauth,
    String oauthId) {

  public User(String email, String password) {
    this(null, email, password, "USER", Boolean.FALSE, null, null);
  }

  public static User createOAuthUser(String email, String password, String oauth, String oauthId) {
    return new User(null, email, password, "USER", Boolean.TRUE, oauth, oauthId);
  }
}
