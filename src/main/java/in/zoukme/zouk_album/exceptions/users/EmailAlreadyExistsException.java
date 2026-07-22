package in.zoukme.zouk_album.exceptions.users;

import in.zoukme.zouk_album.exceptions.AbstractUserException;

public class EmailAlreadyExistsException extends AbstractUserException {

  public EmailAlreadyExistsException(String email) {
    super("Email já cadastrado: " + email);
  }
}
