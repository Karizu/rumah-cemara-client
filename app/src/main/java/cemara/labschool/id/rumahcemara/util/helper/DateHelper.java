package cemara.labschool.id.rumahcemara.util.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {
    public static Date stringToDate(String date)
    {
        Date dateObject;
        try{
            SimpleDateFormat simpleDate =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dateObject=simpleDate.parse(date);
        } catch (Exception e)
        {
            dateObject= Calendar.getInstance().getTime();
        }
        return dateObject;
    }

    public static String dateFormat(Date date)
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM d yyyy");
        return dateFormat.format(date);
    }
}
