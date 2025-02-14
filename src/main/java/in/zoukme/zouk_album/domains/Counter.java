package in.zoukme.zouk_album.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("counters")
public record Counter(@Id Long id, Integer count, Long albumId) {

  public Counter(Long albumId) {
    this(null, 1, albumId);
  }
}
