package in.zoukme.zouk_album.domains.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record User(@Id Long id, String email, String password, String role, Boolean enabled) {

  public User(String email, String password) {
    this(null, email, password, "USER", Boolean.FALSE);
  }
}
