package touk.ticketbookingapp.mapper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeToStringConverter {
    public static String convertTime(LocalDateTime dataTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        return dataTime.format(format);
    }

    public static String convertDate(LocalDateTime dateTime) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(format);
    }

}
