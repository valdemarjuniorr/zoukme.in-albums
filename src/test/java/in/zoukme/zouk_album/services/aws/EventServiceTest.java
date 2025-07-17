package in.zoukme.zouk_album.services.aws;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.oracle.svm.core.annotate.Inject;
import in.zoukme.zouk_album.domains.Event;
import in.zoukme.zouk_album.domains.payments.Package;
import in.zoukme.zouk_album.repositories.events.EventRepository;
import in.zoukme.zouk_album.repositories.events.UpdateEventRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

  @Inject private EventService service;
  @InjectMocks private EventRepository repository;

  @Test
  void update() {
    var event = Mockito.mock(Event.class);
    when(event.id()).thenReturn(1L);
    when(event.packages()).thenReturn(getPackagesDB());
    var request = Mockito.mock(UpdateEventRequest.class);
    when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));
    service.update(1L, request);
  }

  private Set<Package> getPackagesDB() {
    return Set.of(
        new Package(1L, null, "Package 1", "Description 1", BigDecimal.valueOf(100)),
        new Package(2L, null, "Package 2", "Description 2", BigDecimal.valueOf(200)));
  }

  private List<Package> getPackagesRequest() {
    return List.of(
        new Package(1L, null, "Package 1", "Description 1", BigDecimal.valueOf(100)),
        new Package(2L, null, "Package 2", "Description 2", BigDecimal.valueOf(200)));
  }
}
