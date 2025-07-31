package in.zoukme.zouk_album.controllers.payments;

public enum PagBankStatus {
  PAID("Pago"),
  IN_ANALYSIS("Em Analise"),
  DECLINED("Recusado"),
  CANCELED("Cancelado"),
  EXPIRED("Expirado"),
  WAITING("Aguardando");

  private String status;

  PagBankStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
