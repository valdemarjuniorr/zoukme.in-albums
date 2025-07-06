package in.zoukme.zouk_album.domains.payments;

import java.math.BigDecimal;

import in.zoukme.zouk_album.domains.Event;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

@Table("packages")
public record Package(
    @Id Long id,
    AggregateReference<Event, Long> eventId,
    String title,
    String description,
    BigDecimal price) {

  public Package {
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

  public Package(String title, String description, BigDecimal price) {
    this(null, null, title, description, price);
  }
}
