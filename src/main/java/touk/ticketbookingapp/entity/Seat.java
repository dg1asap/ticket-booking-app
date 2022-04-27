package touk.ticketbookingapp.entity;

import java.util.ArrayList;
import java.util.List;

public class Seat {
    private final int id;
    private final int posX;
    private final int posY;
    private List<Reservation> reservations;

    public Seat(int id, int posX, int posY) {
        this.id = id;
        this.reservations = new ArrayList<>();
        this.posX = posX;
        this.posY = posY;
    }

    public int getId() {
        return this.id;
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    public boolean hasSameId(Seat checkedSeat) {
        return this.id == checkedSeat.id;
    }

    public int getRow() {
        return posX;
    }

    public int getColumn() {
        return posY;
    }

    public void book(Reservation reservation) throws IllegalAccessException {
        if (canBook(reservation)) {
            reservation.setRowOfSeat(posX);
            reservation.setColumnOfSeat(posY);
            reservations.add(reservation);
        }
    }

    public boolean hasReservationOnMovieShow(MovieShow movieShow) {
        return reservations.stream()
            .anyMatch(reservations -> reservations.isOverlappingWith(movieShow));
    }

    public boolean hasReservationOverlapsWithReservation(Reservation checkedReservation) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.isOverlappingWith(checkedReservation));
    }

    public List<Reservation> getReservationsOfCustomer (Customer customer) {
        return reservations.stream()
                .filter(reservation -> reservation.belongsToCustomer(customer))
                .toList();
    }

    private boolean canBook(Reservation newReservation) {
        return reservations.stream()
                .noneMatch(reservation -> reservation.isOverlappingWith(newReservation));
    }

}
