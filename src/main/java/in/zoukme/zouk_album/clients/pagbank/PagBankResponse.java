package in.zoukme.zouk_album.clients.pagbank;

import java.util.List;

public record PagBankResponse(String id, List<Link> links) {

  record Link(String rel, String href) {}

  public String getRedirectUrl() {
    return links.stream()
        .filter(link -> link.rel().equals("PAY"))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("redirect url not found"))
        .href;
  }
}
