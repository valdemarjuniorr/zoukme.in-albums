package in.zoukme.zouk_album.security;

import in.zoukme.zouk_album.domains.users.User;
import in.zoukme.zouk_album.domains.users.UserProfile;
import in.zoukme.zouk_album.services.users.UserService;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService service;

  public CustomUserDetailsService(UserService service) {
    this.service = service;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user =
        service
            .findByUsername(username)
            .filter(User::enabled)
            .orElseThrow(
                () -> {
                  return new UsernameNotFoundException("User not found or pending: " + username);
                });

    return new AuthenticatedUser(
        user.email(),
        user.password(),
        List.of(new SimpleGrantedAuthority("ROLE_" + user.role())),
        user.enabled(),
        getProfilePicture(user.email()));
  }

  private String getProfilePicture(String email) {
    return service.findProfileByEmail(email).map(UserProfile::profilePicture).orElse(null);
  }
}
