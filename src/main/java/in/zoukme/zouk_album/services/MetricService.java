package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.domains.metrics.VisitAlbumMetric;
import in.zoukme.zouk_album.repositories.CounterRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

  private final CounterRepository counterRepository;

  public MetricService(CounterRepository counterRepository) {
    this.counterRepository = counterRepository;
  }

  public List<VisitAlbumMetric> findVisitAlbumMetric() {
    return counterRepository.findVisitAlbumMetrics();
  }
}
