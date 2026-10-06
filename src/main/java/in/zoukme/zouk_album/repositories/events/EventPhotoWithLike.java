package in.zoukme.zouk_album.repositories.events;

public record EventPhotoWithLike(
    Long eventPhotoId, String imagePath, Integer count, Boolean liked, Boolean bookmarked) {

  public Boolean isVideo() {
    return imagePath.endsWith(".mp4") || imagePath.endsWith(".mov") 
        || imagePath.endsWith(".webm") || imagePath.endsWith(".ogg") 
        || imagePath.endsWith(".avi") || imagePath.endsWith(".mkv");
  }
}
