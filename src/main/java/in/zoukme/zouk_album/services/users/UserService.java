package in.zoukme.zouk_album.services.users;

import java.util.Optional;

import org.springframework.stereotype.Service;

import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.domains.users.UserProfile;
import in.zoukme.zouk_album.repositories.users.UserRepository;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public void create(UserProfile profile) {
  }

  public Optional<User> findByUsername(String username) {
    return repository.findByEmail(username);
  }
}
