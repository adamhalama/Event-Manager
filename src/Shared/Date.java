package Shared;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Date
{
  /**
   * Covert date string in timestamp
   * @param date - "yyyy-MM-dd HH:mm" format
   * @return timestamp in milliseconds
   */
  public static long dateToTimestamp(String date) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      java.util.Date dateTime = sdf.parse(date);
      return dateTime.getTime();
    } catch (ParseException e) {
      e.getMessage();
    }
    return 0;
  }

  public static String timestampToFullDate(long timestamp) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(timestamp);
  }

  public static String timestampToDate(long timestamp) {
    return new SimpleDateFormat("yyyy-MM-dd").format(timestamp);
  }

  public static String timestampToTime(long timestamp) {
    return new SimpleDateFormat("HH:mm:ss").format(timestamp);
  }

  public static String timestampToHours(long timestamp) {
    return new SimpleDateFormat("HH").format(timestamp);
  }

  public static String timestampToMinutes(long timestamp) {
    return new SimpleDateFormat("mm").format(timestamp);
  }
}
