package in.zoukme.zouk_album.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    return server -> server.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
  }

  private Connector httpToHttpsRedirectConnector() {
    var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
    connector.setScheme("http");
    connector.setPort(80);
    connector.setSecure(Boolean.TRUE);
    connector.setRedirectPort(443);
    return connector;
  }
}
