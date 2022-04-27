package touk.ticketbookingapp.entity;

import java.util.Calendar;

public class MovieShow implements PeriodicEvent {
    private final int Id;
    private final Movie movie;
    private final Calendar start;
    private final Calendar end;

    public MovieShow(int Id, Movie movie, Calendar start, Calendar end) {
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
    public Calendar getStartAsCalendar() {
        return start;
    }

    @Override
    public Calendar getEndAsCalendar() {
        return end;
    }

    private boolean isAtLeastNMinutesBeforeMovie(int minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 15);
        return now.before(start);
    }


}
