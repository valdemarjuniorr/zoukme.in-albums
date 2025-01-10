package in.zoukme.zouk_album.repositories.events;

import java.time.LocalDate;
import java.util.Set;

public final class EventDetails {
  private final Long id;
  private final String title;
  private final String description;
  private final String location;
  private final LocalDate date;
  private final String instagram;
  private final String phoneNumber;
  private final String coverUrl;
  private Set<String> imagePath;
  private final String details;

  public EventDetails(
      Long id,
      String title,
      String description,
      String location,
      LocalDate date,
      String instagram,
      String phoneNumber,
      String coverUrl,
      String details) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.location = location;
    this.date = date;
    this.instagram = instagram;
    this.phoneNumber = phoneNumber;
    this.coverUrl = coverUrl;
    this.details = details;
  }

  public String title() {
    return title;
  }

  public String description() {
    return description;
  }

  public String location() {
    return location;
  }

  public LocalDate date() {
    return date;
  }

  public String instagram() {
    return instagram;
  }

  public String phoneNumber() {
    return phoneNumber;
  }

  public String coverUrl() {
    return coverUrl;
  }

  public Set<String> imagePath() {
    return imagePath;
  }

  public Long getId() {
    return id;
  }

  public void setImagePath(Set<String> imagePath) {
    this.imagePath = imagePath;
  }

  public String getDetails() {
    return this.details;
  }
}
