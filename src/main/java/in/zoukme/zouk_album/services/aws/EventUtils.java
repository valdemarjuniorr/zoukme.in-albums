package in.zoukme.zouk_album.services.aws;

import org.springframework.util.Assert;

class EventUtils {

  static final String BUCKET_NAME = "zoukme.in";

  static String getEventFolderName(String title) {
    Assert.hasText(title, "title must not be empty");

    var eventTitleWithoutSpaces = title.toLowerCase().replace(" ", "-");
    return String.format("%s/%s", "next-events", eventTitleWithoutSpaces);
  }
}
