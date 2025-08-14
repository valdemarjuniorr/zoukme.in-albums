package in.zoukme.zouk_album.clients.pagbank;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class PagBankClientConfig {

  private final PagBankProperties properties;

  public PagBankClientConfig(PagBankProperties properties) {
    this.properties = properties;
  }

  @Bean
  PagBankClient pagBankClient() {
    var restClient =
        RestClient.builder()
            .baseUrl(properties.getBaseUrl())
            .defaultHeader("Authorization", "Bearer " + properties.getToken())
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .build();
    var adapter = RestClientAdapter.create(restClient);
    var factory = HttpServiceProxyFactory.builderFor(adapter).build();

    return factory.createClient(PagBankClient.class);
  }
}
