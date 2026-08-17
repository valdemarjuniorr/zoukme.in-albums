package in.zoukme.zouk_album.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Unified UserDetails implementation that works for both form-based and OAuth2 authentication. This
 * allows controllers to use @AuthenticationPrincipal AuthenticatedUser consistently.
 */
public class AuthenticatedUser implements UserDetails {

  private final String email;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;
  private final boolean enabled;
  private final String picture;

  public AuthenticatedUser(
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      boolean enabled,
      String picture) {
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.enabled = enabled;
    this.picture = picture;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public String getPicture() {
    return picture;
  }
}
