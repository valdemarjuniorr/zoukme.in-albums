package in.zoukme.zouk_album.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AlbumNotFoundException extends RuntimeException {

  public AlbumNotFoundException(Long albumId) {
    super("Album with id " + albumId + " not found");
  }
}
