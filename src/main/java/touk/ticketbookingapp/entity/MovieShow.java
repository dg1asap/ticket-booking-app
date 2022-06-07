package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.exception.room.BookSeatException;

import java.time.LocalDateTime;

public class MovieShow implements TimeEvent {
    private final int Id;
    private final Movie movie;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public MovieShow(int Id, Movie movie, LocalDateTime start, LocalDateTime end) {
        this.Id = Id;
        this.movie = movie;
        this.start = start;
        this.end = end;
    }

    public Reservation createReservationForCustomer(Customer customer) throws BookSeatException {
        if (isAtLeastNMinutesBeforeMovie(15))
            return new Reservation(start, end, customer);
        throw new BookSeatException("Reservations can be made up to 15 minutes before the start of the screening");
    }

    public boolean hasId(int id) {
        return this.Id == id;
    }

    public int getId() {
        return Id;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public LocalDateTime getEnd() {
        return end;
    }

    private boolean isAtLeastNMinutesBeforeMovie(int minutes) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(minutes).isBefore(start);
    }

}
