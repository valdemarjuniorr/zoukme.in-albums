package in.zoukme.zouk_album.repositories.events;

import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.payments.Package;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.util.StringUtils;

public record PackageRequest(Long id, String title, String description, BigDecimal price) {

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

  public Package toDomain(AggregateReference<Event, Long> eventSaved) {
    return new Package(null, eventSaved, title, description, price);
  }

  public static List<PackageRequest> from(Set<Package> packages) {
    return packages.stream()
        .map(pack -> new PackageRequest(pack.id(), pack.title(), pack.description(), pack.price()))
        .toList();
  }

  public Package toEntity(Long eventId) {
    return new Package(null, AggregateReference.to(eventId), title, description, price);
  }
}
