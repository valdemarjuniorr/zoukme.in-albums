package in.zoukme.zouk_album.services;

import in.zoukme.zouk_album.exceptions.CounterNotFoundException;
import in.zoukme.zouk_album.repositories.CounterRepository;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

  private final CounterRepository repository;

  public CounterService(CounterRepository repository) {
    this.repository = repository;
  }

  public Integer getCounterBy(Long albumId) {
    return this.repository.findByAlbumId(albumId).orElseThrow(CounterNotFoundException::new).count();
  }
}
