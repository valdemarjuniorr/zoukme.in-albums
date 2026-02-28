package in.zoukme.zouk_album.repositories.users;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.users.EmailVerificationToken;

public interface EmailVerificationTokenRepository extends ListCrudRepository<EmailVerificationToken, Long> {

  Optional<EmailVerificationToken> findByToken(String token);

}
