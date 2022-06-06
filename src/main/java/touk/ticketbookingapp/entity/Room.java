package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.exception.reservation.ReservationException;
import touk.ticketbookingapp.exception.room.BookSeatException;

import java.security.InvalidParameterException;
import java.util.*;

public class Room {
    private final int number;
    private final List <Seat> seats;

    public Room(int number) {
        this.number = number;
        seats = new ArrayList<>();
    }

    public void addSeat(Seat newSeat) {
        try {
            validateSeat(newSeat);
            seats.add(newSeat);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasNumber(int number) {
        return this.number == number;
    }

    public void bookSeatWithId(Reservation reservation, int seatId)
            throws ReservationException, NoSuchElementException, BookSeatException {

        if (!hasSeatWithId(seatId))
            throw new NoSuchElementException("In room with number: " + number + " is no seat with id: " + seatId);

        Seat seat = getSeatWithId(seatId);
        if (seat.hasReservationOverlapsWithReservation(reservation))
            throw new BookSeatException("In room with number: " + number + " seat with id: " + seatId + " is already booked");

        try {
            seat.book(reservation);
        } catch (ReservationException e) {
            System.out.println(e.getMessage());
            throw new ReservationException("Can't book seat with id " + seatId + " because is invalid reservation");
        }
        setRoomNumberToReservation(reservation);
    }

    public int getNumber() {
        return number;
    }

    public List<Seat> getAvailableSeatsOnMovieShow(MovieShow movieShow) {
        return seats.stream()
                .filter(seat -> !seat.hasReservationOnMovieShow(movieShow))
                .toList();
    }

    public List<Reservation> getReservationsOfCustomerOnMovieShow(Customer customer, MovieShow movieShow) {
        return seats.stream()
                .map(seat -> seat.getReservationsOfCustomer(customer))
                .flatMap(Collection::stream)
                .filter(reservations -> reservations.isOverlappingWith(movieShow))
                .toList();
    }

    public List<Reservation> getReservationsOfCustomer(Customer customer) {
        return seats.stream()
                .map(seat -> seat.getReservationsOfCustomer(customer))
                .flatMap(Collection::stream)
                .toList();
    }

    private void validateSeat(Seat seat) {
        if (hasSeatWithSameId(seat))
            throw new InvalidParameterException("seat id");
    }

    private boolean hasSeatWithId(int id) {
        return seats.stream().anyMatch(seat -> seat.hasId(id));
    }

    private Seat getSeatWithId(int id) {
        for (Seat seat : seats)
            if (seat.hasId(id))
                return seat;
        throw new NoSuchElementException("In room with number: " + number + " is no seat with id: " + id);
    }

    private void setRoomNumberToReservation(Reservation reservation) {
        try {
            reservation.setRoomNumber(number);
        } catch (ReservationException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean hasSeatWithSameId(Seat checkedSeat) {
        return seats.stream().anyMatch(seat -> seat.hasSameId(checkedSeat));
    }

    private void bookSeat(Reservation reservation, Seat seat) throws ReservationException {
        try {
            seat.book(reservation);
        } catch (ReservationException e) {
            System.out.println(e.getMessage());
            throw new ReservationException("Can't book seat with id" + seat.getId());
        }
    }

}
