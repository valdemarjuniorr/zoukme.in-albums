package in.zoukme.zouk_album.services.aws;

public record BucketImage(String eventUrl, String path) {

  public String getFileName() {
    return path.substring(path.lastIndexOf("/") + 1);
  }
}
