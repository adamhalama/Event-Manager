package client.View.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTime
{
    public static String getFormattedDateTime(long timestamp)
    {
        return new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(timestamp);
    }

    public static String getFormattedTime(long timestamp)
    {
        return new SimpleDateFormat("HH:mm:ss").format(timestamp);
    }

    public static long parseStringToTimestamp(String dateString)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date parsedDate = dateFormat.parse(dateString);
            return parsedDate.getTime();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param date a string with a date formatted in dd.MM.yy HH:mm format
     * @return a unix timestamp in milliseconds.
     */
    public static long dateToTimestamp(String date, int hours, int minutes) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

            String hoursStr = String.format("%2s", hours).replace(' ', '0');
            String minutesStr = String.format("%2s", minutes).replace(' ', '0');

            String stringDate = date + " " + hoursStr + ":" + minutesStr ;
            java.util.Date dateTime = sdf.parse(stringDate);
            return dateTime.getTime();
        } catch (ParseException e) {
            e.getMessage();
        }
        return 0;
    }

    public static String getFormattedDate(long timestamp) {
        return new SimpleDateFormat("dd.MM.yyyy").format(timestamp);
    }
}
