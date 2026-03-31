package in.zoukme.zouk_album.exceptions;

/**
 * Class responsible for representing exceptions related to user operations.
 * Throwing this exception will appears a toast with the message of the
 * exception.
 */
public abstract class AbstractUserException extends RuntimeException {

  public AbstractUserException(String message) {
    // Message will be used in the toast that appears when the exception is thrown
    super(message);
  }

}
