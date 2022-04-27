package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.mapper.CalendarToStringConverter;

import java.util.Calendar;

public interface PeriodicEvent {
    Calendar getStartAsCalendar();

    Calendar getEndAsCalendar();

    default String getDayOfStartAsString() {
        Calendar start = getStartAsCalendar();
        return CalendarToStringConverter.convertDate(start);
    }

    default String getTimeOfStartAsString() {
        Calendar start = getStartAsCalendar();
        return CalendarToStringConverter.convertTime(start);
    }

    default String getDayOfEndAsString() {
        Calendar end = getEndAsCalendar();
        return CalendarToStringConverter.convertDate(end);
    }

    default String getTimeOfEndAsString() {
        Calendar end = getEndAsCalendar();
        return CalendarToStringConverter.convertTime(end);
    }

    default boolean isOverlappingWith(PeriodicEvent event) {
        Calendar thisStart = this.getStartAsCalendar();
        Calendar thisEnd = this.getEndAsCalendar();

        Calendar eventStart = event.getStartAsCalendar();
        Calendar eventEnd = event.getEndAsCalendar();

        return thisStart.before(eventEnd) && eventStart.before(thisEnd);
    }

    default boolean isOverlappingWith(Calendar eventStart, Calendar eventEnd) {
        Calendar thisStart = this.getStartAsCalendar();
        Calendar thisEnd = this.getEndAsCalendar();

        return thisStart.before(eventEnd) && eventStart.before(thisEnd);
    }

}
