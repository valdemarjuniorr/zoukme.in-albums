package in.zoukme.zouk_album.security;

import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Converter that handles @AuthenticationPrincipal UserDetails injection for both form-based and
 * OAuth2 authentication methods.
 *
 * <p>When OAuth2User is the principal, it extracts the email and converts it to AuthenticatedUser.
 */
@Component
public class AuthenticatedUserArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().isAssignableFrom(UserDetails.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      org.springframework.web.bind.support.WebDataBinderFactory binderFactory)
      throws Exception {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.isNull(auth) || !auth.isAuthenticated()) {
      return null;
    }

    var principal = auth.getPrincipal();

    // If it's already a UserDetails, return as-is
    if (principal instanceof UserDetails) {
      return principal;
    }

    // If it's an OAuth2User, convert to AuthenticatedUser
    if (principal instanceof OAuth2User oAuth2User) {
      String email = oAuth2User.getAttribute("email");
      var authorities = oAuth2User.getAuthorities();

      return new AuthenticatedUser(email, "", authorities, true);
    }

    return null;
  }
}
