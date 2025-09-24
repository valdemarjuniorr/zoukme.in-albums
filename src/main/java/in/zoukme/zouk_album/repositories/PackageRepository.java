package in.zoukme.zouk_album.repositories;

import java.util.List;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import in.zoukme.zouk_album.domains.payments.Package;


public interface PackageRepository extends ListCrudRepository<Package, Long> {

  @Query("SELECT * FROM packages WHERE event_id = :eventId and visible = true")
  List<Package> findBy(Long eventId);

  @Query("SELECT * FROM packages WHERE event_id = :eventId")
  List<Package> findAllByEventId(Long eventId);
}
