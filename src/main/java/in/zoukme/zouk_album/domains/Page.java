package in.zoukme.zouk_album.domains;

import org.springframework.data.domain.PageRequest;

public record Page(Integer page, Integer size) {

  public PageRequest toPageRequest() {
    return PageRequest.of(this.page - 1, this.size);
  }
}
