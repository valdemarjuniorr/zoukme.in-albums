package in.zoukme.zouk_album.controllers.users;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

import in.zoukme.zouk_album.domains.users.UserProfile;

record UpdateUserForm(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    String instagram, Long userId) {

  public UserProfile toUserProfile() {
    return new UserProfile(id, String.format("%s %s", firstName, lastName), phone, instagram,
        AggregateReference.to(userId));
  }
}
