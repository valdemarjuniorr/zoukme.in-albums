package in.zoukme.zouk_album.controllers.users;

record CreateUserForm(
    String firstName,
    String lastName,
    String email,
    String password,
    String confirmPassword,
    String phone,
    String instagram) {

}
