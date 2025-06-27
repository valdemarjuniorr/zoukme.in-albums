package in.zoukme.zouk_album.controllers.binders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.zoukme.zouk_album.repositories.events.PackageRequest;
import java.beans.PropertyEditorSupport;
import java.util.List;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class PackagesRequestConfig {

  private final ObjectMapper objectMapper;

  public PackagesRequestConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(
        List.class,
        "packages",
        new PropertyEditorSupport() {
          @Override
          public void setAsText(String text) throws IllegalArgumentException {
            try {
              // convert the Json string into List PackageRequest
                System.out.println("Text: " + text);
              var packages =
                  objectMapper.readValue(text, new TypeReference<List<PackageRequest>>() {});
              super.setValue(packages);
            } catch (JsonProcessingException e) {
              throw new RuntimeException(e);
            }
          }
        });
  }
}
