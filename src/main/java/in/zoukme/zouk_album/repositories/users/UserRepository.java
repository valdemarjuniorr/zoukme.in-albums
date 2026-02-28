package in.zoukme.zouk_album.repositories.users;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.users.User;

public interface UserRepository extends ListCrudRepository<User, Long> {

  Optional<User> findByEmail(String username);

  @Modifying
  @Query("UPDATE users SET enabled = true WHERE email = :email")
  public void enableUser(String email);

  @Modifying
  @Query("UPDATE users SET password = :password WHERE email = :email")
  public void updateBy(String email, String password);
}
