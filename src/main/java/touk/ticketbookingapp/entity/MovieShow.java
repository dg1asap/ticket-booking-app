package touk.ticketbookingapp.entity;

import java.time.LocalDateTime;

public class MovieShow implements PeriodicEvent {
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

    public int getId() {
        return Id;
    }

    public boolean hasId(int id) {
        return this.Id == id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Reservation createReservationForCustomer(Customer customer) throws IllegalAccessException {
        if (isAtLeastNMinutesBeforeMovie(15))
            return new Reservation(start, end, customer);
        throw new IllegalAccessException("Reservations can be made up to 15 minutes before the start of the screening");
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
        return now.plusMinutes(15).isBefore(start);
    }


}
