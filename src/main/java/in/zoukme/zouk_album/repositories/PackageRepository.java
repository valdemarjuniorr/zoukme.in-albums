package in.zoukme.zouk_album.repositories;

import in.zoukme.zouk_album.domains.payments.Package;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PackageRepository extends ListCrudRepository<Package, Long> {

    @Query("SELECT * FROM packages WHERE event_id = :eventId")
    List<Package> findBy(Long eventId);
}
