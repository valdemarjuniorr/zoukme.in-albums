package in.zoukme.zouk_album.repositories.events;

import java.math.BigDecimal;
import org.springframework.util.StringUtils;

public record PackageRequest(String title, String description, BigDecimal price) {

  public PackageRequest {
    if (!StringUtils.hasText(title)) {
      throw new IllegalArgumentException("Title cannot be null or blank");
    }
    if (!StringUtils.hasText(description)) {
      throw new IllegalArgumentException("Description cannot be null or blank");
    }
    if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException(
          "Price must be a non-negative value and greater than zero");
    }
  }
}
