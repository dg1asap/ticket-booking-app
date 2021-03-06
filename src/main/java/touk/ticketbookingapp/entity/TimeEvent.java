package touk.ticketbookingapp.entity;

import java.time.LocalDateTime;

public interface TimeEvent {
    LocalDateTime getStart();

    LocalDateTime getEnd();

    default boolean isOverlappingWith(TimeEvent event) {
        LocalDateTime thisStart = this.getStart();
        LocalDateTime thisEnd = this.getEnd();

        LocalDateTime eventStart = event.getStart();
        LocalDateTime eventEnd = event.getEnd();

        return thisStart.isBefore(eventEnd) && eventStart.isBefore(thisEnd);
    }

    default boolean isOverlappingWith(LocalDateTime eventStart, LocalDateTime eventEnd) {
        LocalDateTime thisStart = this.getStart();
        LocalDateTime thisEnd = this.getEnd();

        return thisStart.isBefore(eventEnd) && eventStart.isBefore(thisEnd);
    }

}
