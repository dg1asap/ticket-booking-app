package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.exception.reservation.ReservationException;

import java.util.ArrayList;
import java.util.List;

public class Seat {
    private final int id;
    private final int row;
    private final int column;
    private List<Reservation> reservations;

    public Seat(int id, int row, int column) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.reservations = new ArrayList<>();
    }

    public boolean hasSameId(Seat checkedSeat) {
        return this.id == checkedSeat.id;
    }

    public boolean hasReservationOnMovieShow(MovieShow movieShow) {
        return reservations.stream()
                .anyMatch(reservations -> reservations.isOverlappingWith(movieShow));
    }

    public boolean hasReservationOverlapsWithReservation(Reservation checkedReservation) {
        return reservations.stream()
                .anyMatch(reservation -> reservation.isOverlappingWith(checkedReservation));
    }

    public void book(Reservation reservation) throws ReservationException {
        try {
            if (canBook(reservation)) {
                reservation.setRowOfSeat(row);
                reservation.setColumnOfSeat(column);
                reservations.add(reservation);
            }
        } catch (ReservationException e) {
            System.out.println(e.getMessage());
            throw new ReservationException("Can't use reservation and book seat in row " + reservation.getRowOfSeat() + " and column " + reservation.getColumnOfSeat());
        }
    }

    public int getId() {
        return this.id;
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
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
