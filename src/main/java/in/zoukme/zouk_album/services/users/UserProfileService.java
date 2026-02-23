package in.zoukme.zouk_album.services.users;

import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

  private final UserService userService;

  public UserProfileService(UserService userService) {
    this.userService = userService;
  }

  public void create(String fullName, String phone, String instagram, Long userId) {
  }
}
