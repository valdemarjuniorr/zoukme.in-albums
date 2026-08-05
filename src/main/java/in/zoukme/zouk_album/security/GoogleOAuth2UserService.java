package in.zoukme.zouk_album.security;

import in.zoukme.zouk_album.services.users.UserService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {

  private UserService userService;

  public GoogleOAuth2UserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    var oauthUser = super.loadUser(userRequest);
    String email = oauthUser.getAttribute("email");
    String name = oauthUser.getAttribute("name");

    var oauthProvider = userRequest.getClientRegistration().getRegistrationId();
    String oauthId = null;
    String picture = null;
    if (oauthProvider.equalsIgnoreCase("facebook")) {
      oauthId = oauthUser.getAttribute("id");
      picture = getFacebookProfilePicture(oauthUser);
    } else if (oauthProvider.equalsIgnoreCase("google")) {
      oauthId = oauthUser.getAttribute("sub");
      picture = oauthUser.getAttribute("picture");
    }
    userService.createOAuthUser(name, email, oauthProvider, oauthId, picture);

    // Return an OAuth2User that wraps AuthenticatedUser for consistent UserDetails injection
    return new DefaultOAuth2User(
        List.of(new SimpleGrantedAuthority("ROLE_USER")), oauthUser.getAttributes(), "email");
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
}
