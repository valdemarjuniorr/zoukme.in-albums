package in.zoukme.zouk_album.clients.pagbank;

import org.springframework.stereotype.Service;

@Service
public class PagBankService {

  private final PagBankClient pagBankClient;

  public PagBankService(PagBankClient pagBankClient) {
    this.pagBankClient = pagBankClient;
  }

  public PagBankResponse createCheckOut(CreateCheckoutRequest request) {
    return pagBankClient.createCheckOut(request);
  }
}