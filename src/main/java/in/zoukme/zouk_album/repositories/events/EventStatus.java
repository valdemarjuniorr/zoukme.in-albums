package in.zoukme.zouk_album.repositories.events;

public enum EventStatus {
  DRAFT("Rascunho"),
  CONFIRMED("Confirmado"),
  CANCELLED("Cancelado");

  private String name;

  EventStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
