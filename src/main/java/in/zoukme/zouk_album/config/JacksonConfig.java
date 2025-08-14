package in.zoukme.zouk_album.config;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

  @Bean
  ObjectMapper objectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.addMixIn(InputStream.class, InputStreamMixIn.class);

    return objectMapper;
  }

  @JsonIgnoreType
  abstract static class InputStreamMixIn {
    // This mixin tells Jackson to ignore InputStream properties
  }
}
