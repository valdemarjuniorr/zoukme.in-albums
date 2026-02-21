package in.zoukme.zouk_album.repositories.users;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.users.User;

public interface UserRepository extends ListCrudRepository<User, Long> {

  Optional<User> findByEmail(String username);
}
