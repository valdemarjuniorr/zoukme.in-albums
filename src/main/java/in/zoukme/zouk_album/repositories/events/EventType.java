package in.zoukme.zouk_album.repositories.events;

public enum EventType {
  PARTY("Baile"),
  CONGRESS("Congresso"),
  WORKSHOP("Workshop"),
  OTHER("Outro");

  private String name;

  EventType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
