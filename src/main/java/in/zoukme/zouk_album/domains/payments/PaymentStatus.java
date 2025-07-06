package in.zoukme.zouk_album.domains.payments;

public enum PaymentStatus {
  PAID("Pago"),
  IN_ANALYSIS("Em Análise"),
  DECLINED("Recusado"),
  CANCELED("Cancelado"),
  WAITING("Aguardando");

  private String status;

  PaymentStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }
}
