package in.zoukme.zouk_album.config;

import in.zoukme.zouk_album.security.AuthenticatedUserArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final AuthenticatedUserArgumentResolver argumentResolver;

  public WebMvcConfig(AuthenticatedUserArgumentResolver argumentResolver) {
    this.argumentResolver = argumentResolver;
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(argumentResolver);
  }
}
