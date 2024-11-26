package in.zoukme.zouk_album.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Event not found")
public class EventNotFoundException extends RuntimeException{}
