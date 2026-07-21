package in.zoukme.zouk_album.services.aws;

import org.springframework.util.Assert;

class EventUtils {

  private static final String TITLE_REQUIRED_MGS = "title must not be empty";
  static final String BUCKET_NAME = "zoukme.in";

  static String getEventFolderName(String title) {
    Assert.hasText(title, TITLE_REQUIRED_MGS);

    return "%s/%s".formatted("next-events", getFormatEventName(title));
  }

  static String getFormatEventName(String title) {
    Assert.hasText(title, TITLE_REQUIRED_MGS);

    var normalized =
        java.text.Normalizer.normalize(title, java.text.Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    return normalized.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
  }
}
