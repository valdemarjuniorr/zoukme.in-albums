package in.zoukme.zouk_album.services.aws;

import org.springframework.util.Assert;

class EventUtils {

  static final String BUCKET_NAME = "zoukme.in";

  static String getEventFolderName(String title) {
    Assert.hasText(title, "title must not be empty");

    return "%s/%s".formatted("next-events", getFormatEventName(title));
  }

  static String getFormatEventName(String title) {
    return title.toLowerCase().replace(" ", "-");
  }
}
