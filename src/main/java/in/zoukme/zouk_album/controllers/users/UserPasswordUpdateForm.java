package in.zoukme.zouk_album.controllers.users;

record UserPasswordUpdateForm(
    String newPassword,
    String confirmNewPassword) {

  public Boolean matches() {
    return newPassword.equals(confirmNewPassword);
  }
}

