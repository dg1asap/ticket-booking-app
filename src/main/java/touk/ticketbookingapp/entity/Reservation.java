package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.exception.reservation.ReservationException;

import java.time.LocalDateTime;

public class Reservation implements TimeEvent {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final Customer booker;
    private int roomNumber;
    private int row;
    private int column;

    public Reservation(LocalDateTime form, LocalDateTime to, Customer booker) {
        this.start = form;
        this.end = to;
        this.booker = booker;
        this.roomNumber = 0;
        this.row = 0;
        this.column = 0;
    }

    public boolean belongsToCustomer(Customer booker) {
        return this.booker == booker;
    }

    public void setRoomNumber(int number) throws ReservationException {
        if (this.roomNumber != 0)
            throw new ReservationException("This reservation is already in use, you can't override room number");
        this.roomNumber = number;
    }

    public void setRowOfSeat(int row) throws ReservationException {
        if (this.row != 0)
            throw new ReservationException("This reservation is already in use, you can't override row of seat");
        this.row = row;
    }

    public void setColumnOfSeat(int column) throws ReservationException{
        if (this.column != 0)
            throw new ReservationException("This reservation is already in use, you can't override column of seat");
        this.column = column;
    }

    @Override
    public LocalDateTime getStart() {
        return start;
    }

    @Override
    public LocalDateTime getEnd() {
        return end;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getRowOfSeat() {
        return row;
    }

    public int getColumnOfSeat() {
        return column;
    }

}
