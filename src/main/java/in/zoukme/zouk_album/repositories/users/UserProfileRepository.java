package in.zoukme.zouk_album.repositories.users;

import org.springframework.data.repository.ListCrudRepository;

import in.zoukme.zouk_album.domains.users.UserProfile;

public interface UserProfileRepository extends ListCrudRepository<UserProfile, Long> {
}
