package in.zoukme.zouk_album.services.users;

public enum UserVerificationError {
  EXPIRED_TOKEN("Token has expired"),
  EMAIL_NOT_FOUND("Email not found");

  private String message;

  UserVerificationError(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
