package in.zoukme.zouk_album.services.token;

import org.springframework.stereotype.Service;

import in.zoukme.zouk_album.domains.users.EmailVerificationToken;
import in.zoukme.zouk_album.repositories.users.EmailVerificationTokenRepository;

@Service
public class EmailVerificationTokenService {

  private final EmailVerificationTokenRepository repository;

  public EmailVerificationTokenService(EmailVerificationTokenRepository repository) {
    this.repository = repository;
  }

  public EmailVerificationToken findBy(String token) {
    return repository.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
  }

  public void create(EmailVerificationToken token) {
    repository.save(token);
  }

  public void delete(EmailVerificationToken verificationToken) {
    repository.delete(verificationToken);
  }

}
