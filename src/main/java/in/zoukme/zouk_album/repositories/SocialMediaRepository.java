package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.SocialMedia;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface SocialMediaRepository extends ListCrudRepository<SocialMedia, Long> {

    @Modifying
    @Query("DELETE FROM social_media WHERE event_id = :eventId")
    void deleteSocialMediaByEventId(Long eventId);
}
