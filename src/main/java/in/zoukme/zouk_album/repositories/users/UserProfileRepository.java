package in.zoukme.zouk_album.repositories.users;

import in.zoukme.zouk_album.domains.users.UserProfile;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface UserProfileRepository
    extends ListCrudRepository<UserProfile, Long>,
        ListPagingAndSortingRepository<UserProfile, Long> {

  @Query(
      "SELECT up.* FROM users_profile up INNER JOIN users u ON up.user_id = u.id WHERE u.email ="
          + " :email")
  Optional<UserProfile> findByEmail(String email);

  Page<UserProfile> findAllByOrderByIdDesc(Pageable pageable);

  @Modifying
  @Query("UPDATE users_profile SET picture = :picture WHERE user_id = :userId")
  void updatePictureBy(Long userId, String picture);
}
