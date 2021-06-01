package client.View.Helpers;

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
}
