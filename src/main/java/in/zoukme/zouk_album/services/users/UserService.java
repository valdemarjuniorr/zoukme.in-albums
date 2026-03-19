package in.zoukme.zouk_album.services.users;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.zoukme.zouk_album.domains.users.EmailVerificationToken;
import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.domains.users.UserProfile;
import in.zoukme.zouk_album.repositories.users.UserProfileRepository;
import in.zoukme.zouk_album.repositories.users.UserRepository;
import in.zoukme.zouk_album.services.aws.ses.EmailService;
import in.zoukme.zouk_album.services.token.EmailVerificationTokenService;

@Service
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository repository;
  private final UserProfileRepository profileRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailVerificationTokenService tokenService;
  private final EmailService emailService;

  public UserService(UserRepository repository, UserProfileRepository profileRepository,
      PasswordEncoder passwordEncoder, EmailVerificationTokenService tokenService, EmailService emailService) {
    this.repository = repository;
    this.profileRepository = profileRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
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
    tokenService.create(verificationToken);

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
    var verificationToken = tokenService.findBy(token);
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
      tokenService.delete(verificationToken);
      log.info("Email verified for user with email: {}", user.email());
    });

    return null;
  }

  // Method responisible to resend the email with a new token to redefine password
  public UserVerificationError resend(String email) {
    var profile = profileRepository.findByEmail(email);
    if (profile.isEmpty()) {
      log.warn("Profile not found for email: {}", email);
      return UserVerificationError.EMAIL_NOT_FOUND;
    }
    var token = new EmailVerificationToken(profile.get());
    tokenService.create(token);
    emailService.send(
        new UserResetPasswordEmailTemplate(profile.get().fullName(), email, token.token()));
    log.info("Resent email verification token to email: {}", email);

    return null;
  }

  public EmailVerificationToken findTokenBy(String token) {
    return tokenService.findBy(token);
  }

  public void redefinePasswordValidate(String token, String password, String confirmPassword) {
    var tokenFound = tokenService.findBy(token);
    if (tokenFound.isExpired()) {
      log.warn("Email verification token expired for token: {}", token);
      throw new IllegalArgumentException("Token expired");
    }

    repository.findById(tokenFound.userId().getId()).ifPresent(user -> {
      repository.updateBy(user.email(), passwordEncoder.encode(password));
      // tokenService.delete(tokenFound);
      log.info("Password redefined for user with email: {}", user.email());
    });
  }

  public Optional<User> getUserLogged() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return findByUsername(authentication.getName());
  }

  public Optional<UserDetails> getCurrentUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(authentication) && authentication.getPrincipal() instanceof UserDetails) {
      return Optional.of((UserDetails) authentication.getPrincipal());
    }
    return Optional.empty();
  }

  public Boolean hasRole(String role) {
    var user = getCurrentUser();
    return user.isPresent() && user.get().getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals(role));
  }

}
