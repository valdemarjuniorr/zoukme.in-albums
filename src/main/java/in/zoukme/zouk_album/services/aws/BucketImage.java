package in.zoukme.zouk_album.services.aws;

import java.io.File;

public record BucketImage(String eventUrl, String path) {

  public String getFileName() {
    return path.substring(path.lastIndexOf("/") + 1);
  }

  /**
   * Get the path from the event index folder.
   * https://s3.sa-east-1.amazonaws.com/zoukme.in/events/baile-do-sorriso-2022/aulas/DSC_1997.jpg
   *
   * @return /zoukme.in/events/baile-do-sorriso-2022/aulas/DSC_1997.jpg
   */
  public String getPathFromEventIndex() {
    return File.separator + "events" + File.separator + path.substring(path.indexOf(eventUrl));
  }
}
