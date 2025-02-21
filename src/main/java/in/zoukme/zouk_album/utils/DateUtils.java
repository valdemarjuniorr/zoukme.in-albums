package in.zoukme.zouk_album.utils;

import java.time.LocalDate;
import java.time.Month;

public final class DateUtils {

  /** This method returns a descriptive date as 21/Fev/2025 */
  public static String getDescriptiveDate(LocalDate date) {
    return String.format(
        "%d/%s/%d", date.getDayOfMonth(), getDescriptiveMonth(date.getMonth()), date.getYear());
  }

  private static String getDescriptiveMonth(Month month) {
    return switch (month) {
      case JANUARY -> "Jan";
      case FEBRUARY -> "Fev";
      case MARCH -> "Mar";
      case APRIL -> "Abr";
      case MAY -> "Mai";
      case JUNE -> "Jun";
      case JULY -> "Jul";
      case AUGUST -> "Ago";
      case SEPTEMBER -> "Set";
      case OCTOBER -> "Out";
      case NOVEMBER -> "Nov";
      case DECEMBER -> "Dez";
    };
  }
}
