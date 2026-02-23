package in.zoukme.zouk_album.domains.users;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("email_verification_tokens")
public record EmailVerificationToken(@Id Long id, String token, AggregateReference<User, Long> userId,
    LocalDateTime expiryDate) {

  public EmailVerificationToken(User user) {
    var generatedToken = UUID.randomUUID().toString();
    this(null, generatedToken, AggregateReference.to(user.id()), LocalDateTime.now().plusDays(3));
  }

  public Boolean isExpired() {
    return LocalDateTime.now().isAfter(expiryDate);
  }
}
