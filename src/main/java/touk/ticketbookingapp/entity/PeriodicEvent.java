package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.mapper.LocalDateTimeToStringConverter;

import java.time.LocalDateTime;

public interface PeriodicEvent {
    LocalDateTime getStartAsLocalDateTime();

    LocalDateTime getEndAsLocalDateTime();

    default String getDayOfStartAsString() {
        LocalDateTime start = getStartAsLocalDateTime();
        return LocalDateTimeToStringConverter.convertDate(start);
    }

    default String getTimeOfStartAsString() {
        LocalDateTime start = getStartAsLocalDateTime();
        return LocalDateTimeToStringConverter.convertTime(start);
    }

    default String getDayOfEndAsString() {
        LocalDateTime end = getEndAsLocalDateTime();
        return LocalDateTimeToStringConverter.convertDate(end);
    }

    default String getTimeOfEndAsString() {
        LocalDateTime end = getEndAsLocalDateTime();
        return LocalDateTimeToStringConverter.convertTime(end);
    }

    default boolean isOverlappingWith(PeriodicEvent event) {
        LocalDateTime thisStart = this.getStartAsLocalDateTime();
        LocalDateTime thisEnd = this.getEndAsLocalDateTime();

        LocalDateTime eventStart = event.getStartAsLocalDateTime();
        LocalDateTime eventEnd = event.getEndAsLocalDateTime();

        return thisStart.isBefore(eventEnd) && eventStart.isBefore(thisEnd);
    }

    default boolean isOverlappingWith(LocalDateTime eventStart, LocalDateTime eventEnd) {
        LocalDateTime thisStart = this.getStartAsLocalDateTime();
        LocalDateTime thisEnd = this.getEndAsLocalDateTime();

        return thisStart.isBefore(eventEnd) && eventStart.isBefore(thisEnd);
    }

}
