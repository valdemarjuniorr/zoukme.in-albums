package in.zoukme.zouk_album.clients.pagbank;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface PagBankClient {

  @PostExchange("/checkouts")
  PagBankResponse createCheckOut(@RequestBody CreateCheckoutRequest request);

  @PostExchange("/checkouts/{checkoutId}/inactivate")
  void inactivateCheckOut(@PathVariable String checkoutId);
}
