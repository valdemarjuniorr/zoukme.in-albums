package in.zoukme.zouk_album.security;

import in.zoukme.zouk_album.services.users.UserService;
import java.util.List;
import java.util.Map;
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
    String googleId = oauthUser.getAttribute("sub");
    String email = oauthUser.getAttribute("email");
    String name = oauthUser.getAttribute("name");
    String picture = oauthUser.getAttribute("picture");
    Boolean verified = oauthUser.getAttribute("email_verified");

    userService.createOAuthUser(name, email, "GOOGLE", googleId, picture, verified);

    // Return an OAuth2User that wraps AuthenticatedUser for consistent UserDetails injection
    return new DefaultOAuth2User(
        List.of(new SimpleGrantedAuthority("ROLE_USER")),
        oauthUser.getAttributes(),
        "email");
  }
}

