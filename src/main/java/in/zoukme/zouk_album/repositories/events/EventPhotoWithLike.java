package in.zoukme.zouk_album.repositories.events;

public record EventPhotoWithLike(Long eventPhotoId, String imagePath, Integer count, Boolean liked) {
}
