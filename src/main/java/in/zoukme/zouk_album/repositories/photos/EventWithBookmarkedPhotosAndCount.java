package in.zoukme.zouk_album.repositories.photos;

import in.zoukme.zouk_album.utils.DateUtils;
import java.time.LocalDate;

public record EventWithBookmarkedPhotosAndCount(
    Long id,
    String title,
    String location,
    LocalDate date,
    String coverUrl,
    String eventUrl,
    Long count) {

  public String getDescriptiveDate() {
    return DateUtils.getDescriptiveDate(this.date);
  }
}
