package in.zoukme.zouk_album.repositories.users;

import in.zoukme.zouk_album.domains.users.User;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {

  Optional<User> findByEmail(String username);

  @Modifying
  @Query("UPDATE users SET enabled = true WHERE email = :email")
  void enableUser(String email);

  @Modifying
  @Query("UPDATE users SET password = :password WHERE email = :email")
  void updateBy(String email, String password);

  long count();

  @Modifying
  @Query("UPDATE users SET oauth = :oauth, oauth_id = :oauthId WHERE email = :email")
  void updateOAuthBy(String email, String oauth, String oauthId);
}
