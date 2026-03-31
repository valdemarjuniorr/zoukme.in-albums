package in.zoukme.zouk_album.exceptions.users;

import in.zoukme.zouk_album.exceptions.AbstractUserException;

public class UserPasswordDoesNotMatchException extends AbstractUserException {

  public UserPasswordDoesNotMatchException() {
    super("A senhas não coincidem!");
  }
}
