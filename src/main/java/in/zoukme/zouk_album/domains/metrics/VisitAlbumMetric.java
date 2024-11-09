package in.zoukme.zouk_album.domains.metrics;

import java.time.LocalDate;

public record VisitAlbumMetric(String title, LocalDate eventDate, String thumbUrl, Integer count) {}
