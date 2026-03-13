package in.zoukme.zouk_album.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.zoukme.zouk_album.services.users.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
  private final UserService service;

  public CustomUserDetailsService(UserService service) {
    this.service = service;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("Loading user by username: " + username);
    var user = service.findByUsername(username)
        .filter(u -> u.enabled()).orElseThrow(() -> {
          return new UsernameNotFoundException("User not found or pending: " + username);
        });
    return org.springframework.security.core.userdetails.User
        .withUsername(user.email())
        .password(user.password())
        .roles(user.role())
        .build();
  }

}
