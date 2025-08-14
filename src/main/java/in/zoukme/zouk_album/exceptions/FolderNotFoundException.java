package in.zoukme.zouk_album.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Folder not found")
public class FolderNotFoundException extends RuntimeException {

  public FolderNotFoundException(String message) {
    super("Folder not found %s".formatted(message));
  }
}
