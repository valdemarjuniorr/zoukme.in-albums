package in.zoukme.zouk_album.clients.pagbank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PagBankService {

  private static final Logger log = LoggerFactory.getLogger(PagBankService.class);
  private final PagBankClient client;

  public PagBankService(PagBankClient client) {
    this.client = client;
  }

  public PagBankResponse createCheckOut(CreateCheckoutRequest request) {
    var response = client.createCheckOut(request);
    log.info("Created checkout with id: {}", response.id());
    return response;
  }

  public void inactivateCheckout(String transactionId) {
    client.inactivateCheckout(transactionId);
    log.info("Inactivated checkout with transactionId: {}", transactionId);
  }
}
