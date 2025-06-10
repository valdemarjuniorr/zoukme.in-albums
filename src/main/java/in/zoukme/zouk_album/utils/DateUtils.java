package in.zoukme.zouk_album.utils;

import java.time.LocalDate;
import java.time.Month;

public final class DateUtils {

  /** This method returns a descriptive date as 21/Fev/2025 */
  public static String getDescriptiveDate(LocalDate date) {
    return String.format(
        "%d de %s, %d", date.getDayOfMonth(), getDescriptiveMonth(date.getMonth()), date.getYear());
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
