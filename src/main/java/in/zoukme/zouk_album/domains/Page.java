package in.zoukme.zouk_album.domains;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public record Page(Integer page, Integer size) {

  private static final Integer RANGE = 3;

  public Page {
    if (size > 50) {
      size = 50;
    }
  }

  public PageRequest toPageRequest() {
    return PageRequest.of(this.page - 1, this.size);
  }

  public List<String> generatePagination(Integer totalPages) {
    var pages = new ArrayList<String>();

    if (page > RANGE) {
      pages.add("...");
    }
    for (var i = 1; i <= totalPages; i++) {
      if ((page - RANGE) < i && (page + RANGE) > i) {
        pages.add(String.valueOf(i));
      }
    }

    if (totalPages - page >= RANGE) {
      pages.add("...");
    }
    return pages;
  }
}
