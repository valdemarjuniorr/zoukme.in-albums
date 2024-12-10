package in.zoukme.zouk_album.repositories.events;

import java.time.LocalDate;
import java.util.List;

public record EventDetails(
    String title,
    String description,
    String location,
    LocalDate date,
    String instagram,
    String phoneNumber,
    String coverUrl,
    List<String> imagePath) {}
