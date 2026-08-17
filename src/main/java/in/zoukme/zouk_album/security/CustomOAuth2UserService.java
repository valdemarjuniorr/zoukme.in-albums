package in.zoukme.zouk_album.security;

import in.zoukme.zouk_album.services.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

  private UserService userService;

  public CustomOAuth2UserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info(
        "Loading user from OAuth2 provider: {}",
        userRequest.getClientRegistration().getRegistrationId());
    var oauthUser = super.loadUser(userRequest);
    var authenticatedUser =
        new OAuthUser(oauthUser, userRequest.getClientRegistration().getRegistrationId());
    userService.createOAuthUser(authenticatedUser);

    log.info(
        "User loaded and processed: email={}, name={}, provider={}, providerId={}, pricture={}",
        authenticatedUser.getEmail(),
        authenticatedUser.getName(),
        authenticatedUser.getProvider(),
        authenticatedUser.getProviderId(),
        authenticatedUser.getPicture());
    // Return an OAuth2User that wraps AuthenticatedUser for consistent UserDetails injection
    return authenticatedUser;
  }
}
