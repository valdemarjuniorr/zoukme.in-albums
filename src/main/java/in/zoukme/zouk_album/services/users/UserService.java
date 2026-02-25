package in.zoukme.zouk_album.services.users;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.zoukme.zouk_album.domains.users.EmailVerificationToken;
import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.domains.users.UserProfile;
import in.zoukme.zouk_album.repositories.users.EmailVerificationTokenRepository;
import in.zoukme.zouk_album.repositories.users.UserProfileRepository;
import in.zoukme.zouk_album.repositories.users.UserRepository;
import in.zoukme.zouk_album.services.aws.ses.EmailService;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository repository;
  private final UserProfileRepository profileRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailVerificationTokenRepository tokenRepository;
  private final EmailService emailService;

  public UserService(UserRepository repository, UserProfileRepository profileRepository,
      PasswordEncoder passwordEncoder, EmailVerificationTokenRepository tokenRepository, EmailService emailService) {
    this.repository = repository;
    this.profileRepository = profileRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenRepository = tokenRepository;
    this.emailService = emailService;
  }

  @Transactional
  public void create(String fullName, String phone, String instagram, String email, String password,
      String confirmPassword) {
    var user = new User(email, passwordEncoder.encode(password));
    var newUser = repository.save(user);
    var profile = new UserProfile(fullName, phone, instagram, newUser);
    profileRepository.save(profile);
    var verificationToken = new EmailVerificationToken(newUser);
    tokenRepository.save(verificationToken);

    emailService.send(
        new UserPendingEmailTemplate(email, verificationToken.token()));
    log.info("User created with email: {}", email);
  }

  public Optional<User> findByUsername(String username) {
    return repository.findByEmail(username);
  }

  public Optional<UserProfile> findProfileByEmail(String email) {
    return profileRepository.findByEmail(email);
  }

  public UserVerificationError confirmUserBy(String token) {
    var verificationToken = tokenRepository.findByToken(token);
    if (Objects.isNull(verificationToken)) {
      log.warn("Email verification token not found for token: {}", token);
      return UserVerificationError.EMAIL_NOT_FOUND;
    }
    if (verificationToken.isExpired()) {
      log.warn("Email verification token expired for token: {}", token);
      return UserVerificationError.EXPIRED_TOKEN;
    }

    repository.findById(verificationToken.userId().getId()).ifPresent(user -> {
      repository.enableUser(user.email());
      tokenRepository.delete(verificationToken);
      log.info("Email verified for user with email: {}", user.email());
    });

    return null;
  }

  public UserVerificationError resend(String email) {
    var user = repository.findByEmail(email);
    if (Objects.isNull(user)) {
      log.warn("User not found for email: {}", email);
      return UserVerificationError.EMAIL_NOT_FOUND;
    }
    // send email
    return null;
  }
}
