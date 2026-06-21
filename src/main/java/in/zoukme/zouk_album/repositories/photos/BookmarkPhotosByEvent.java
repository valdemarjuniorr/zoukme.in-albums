package in.zoukme.zouk_album.repositories.photos;

public record BookmarkPhotosByEvent(
    Long eventPhotoId, Long eventId, Long subEventId, String imagePath) {}
