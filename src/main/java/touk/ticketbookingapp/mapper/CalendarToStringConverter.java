package touk.ticketbookingapp.mapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarToStringConverter {
    public static String convertTime(Calendar calendar) {
        Date dateStart = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(dateStart);
    }

    public static String convertDate(Calendar calendar) {
        if(0 == calendar.get(Calendar.MONTH))
            return calendar.get(Calendar.DATE) + "/12/" + (calendar.get(Calendar.YEAR) - 1);
        return calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR);
    }
}
