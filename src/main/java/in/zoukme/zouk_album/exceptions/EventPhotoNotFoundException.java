package in.zoukme.zouk_album.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Event photo not found")
public class EventPhotoNotFoundException extends RuntimeException {}
