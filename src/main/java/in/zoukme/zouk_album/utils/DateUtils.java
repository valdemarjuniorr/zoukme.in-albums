package in.zoukme.zouk_album.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public final class DateUtils {

  /** This method returns a descriptive date as 21/Fev/2025 */
  public static String getDescriptiveDate(LocalDate date) {
    return String.format(
        "%d de %s, %d", date.getDayOfMonth(), getDescriptiveMonth(date.getMonth()), date.getYear());
  }

  public static String getDescriptiveDateTime(LocalDateTime date) {
    return String.format(
        "%d de %s, %d às %02d:%02d",
        date.getDayOfMonth(),
        getDescriptiveMonth(date.getMonth()),
        date.getYear(),
        date.getHour(),
        date.getMinute());
  }

  /** This method returns a LocalDateTime with the initial time of the day */
  public static LocalDateTime startDateTime(LocalDate date) {
    return LocalDateTime.of(date, LocalDateTime.MIN.toLocalTime());
  }

  public static LocalDateTime endDateTime(LocalDate date) {
    return LocalDateTime.of(date, LocalDateTime.MAX.toLocalTime());
  }

  public static LocalDateTime beginningOfTheYear(Integer year) {
    return LocalDateTime.of(year, Month.JANUARY, 1, 0, 0);
  }

  public static LocalDateTime beginningOfTheCurrentYear() {
    return beginningOfTheYear(LocalDateTime.now().getYear());
  }

  private static String getDescriptiveMonth(Month month) {
    return switch (month) {
      case JANUARY -> "Janeiro";
      case FEBRUARY -> "Fevereiro";
      case MARCH -> "Março";
      case APRIL -> "Abril";
      case MAY -> "Maio";
      case JUNE -> "Junho";
      case JULY -> "Julho";
      case AUGUST -> "Agosto";
      case SEPTEMBER -> "Setembro";
      case OCTOBER -> "Outubro";
      case NOVEMBER -> "Novembro";
      case DECEMBER -> "Dezembro";
    };
  }
}
