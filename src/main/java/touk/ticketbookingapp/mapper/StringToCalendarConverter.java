package touk.ticketbookingapp.mapper;

import java.util.Arrays;
import java.util.Calendar;

//7:20-1/2/2022
public class StringToCalendarConverter {
    public static Calendar convert(String string) {
        String[] timeAsString = string.split("-")[0].split(":");
        String[] dayAsString = string.split("-")[1].split("/");

        int[] time = Arrays.stream(timeAsString).mapToInt(Integer::parseInt).toArray();
        int[] date = Arrays.stream(dayAsString).mapToInt(Integer::parseInt).toArray();

        int day = date[0];
        int month = date[1];
        int year = date[2];
        int hour = time[0];
        int minute = time[1];

        return new Calendar.Builder()
                .setDate(year, month, day)
                .setTimeOfDay(hour, minute, 0)
                .build();
    }
}
