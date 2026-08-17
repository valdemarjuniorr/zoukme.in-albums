package in.zoukme.zouk_album.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuthUser implements OAuth2User {

  private final OAuth2User oauth2User;
  private String email;
  private String name;
  private String provider;
  private String providerId;
  private String picture;

  public OAuthUser(OAuth2User oAuthUser, String provider) {
    this.oauth2User = oAuthUser;
    this.email = oAuthUser.getAttribute("email");
    this.name = oAuthUser.getAttribute("name");
    this.provider = provider;

    if (provider.equalsIgnoreCase("facebook")) {
      this.providerId = oAuthUser.getAttribute("id");
      this.picture = getFacebookProfilePicture(oAuthUser);
    } else if (provider.equalsIgnoreCase("google")) {
      this.providerId = oAuthUser.getAttribute("sub");
      this.picture = oAuthUser.getAttribute("picture");
    }
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getProvider() {
    return provider;
  }

  public String getProviderId() {
    return providerId;
  }

  public String getPicture() {
    return picture;
  }

  private String getFacebookProfilePicture(OAuth2User oauthUser) {
    // 1. Get the top-level "picture" attribute (returns a Map)
    Map<String, Object> picture = oauthUser.getAttribute("picture");

    if (Objects.nonNull(picture)) {
      // 2. Get the inner "data" attribute (returns a Map)
      Map<String, Object> data = (Map<String, Object>) picture.get("data");

      if (Objects.nonNull(data)) {
        // 3. Extract the "url" string
        return (String) data.get("url");
      }
    }
    return null;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.oauth2User.getAttributes();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_USER"));
  }
}
