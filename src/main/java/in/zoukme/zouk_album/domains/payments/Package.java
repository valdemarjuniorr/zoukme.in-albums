package in.zoukme.zouk_album.domains.payments;

import in.zoukme.zouk_album.domains.Event;
import java.math.BigDecimal;
import java.util.Objects;
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

  public boolean equals(Package other) {
    if (this == other) {
      return true;
    }
    if (Objects.isNull(other)) {
      return false;
    }
    if (Objects.nonNull(this.title)
        && Objects.nonNull(other.title)
        && !this.title.equals(other.title)) {
      return false;
    }
    if (Objects.nonNull(this.description)
        && Objects.nonNull(other.description)
        && !this.description.equals(other.description)) {
      return false;
    }
    if (Objects.nonNull(this.price) && Objects.nonNull(other.price)) {
      return this.price.compareTo(other.price) == 0;
    }
    return true;
  }
}
