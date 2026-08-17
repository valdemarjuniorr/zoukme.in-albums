package in.zoukme.zouk_album.controllers.users;

import in.zoukme.zouk_album.domains.users.UserProfile;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

record UpdateUserForm(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    String instagram,
    Long userId) {

  public UserProfile toUserProfile() {
    return new UserProfile(
        null,
        String.format("%s %s", firstName, lastName),
        phone,
        instagram,
        AggregateReference.to(userId),
        null);
  }
}
