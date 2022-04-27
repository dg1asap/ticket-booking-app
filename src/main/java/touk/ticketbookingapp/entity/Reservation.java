package touk.ticketbookingapp.entity;

import java.util.Calendar;

public class Reservation implements PeriodicEvent {
    private final Calendar start;
    private final Calendar end;
    private final Customer booker;
    private int roomNumber;
    private int row;
    private int column;

    public Reservation(Calendar form, Calendar to, Customer booker) {
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

    @Override
    public Calendar getStartAsCalendar() {
        return start;
    }

    @Override
    public Calendar getEndAsCalendar() {
        return end;
    }

    public void setRoomNumber(int number) throws IllegalAccessException {
        if (this.roomNumber != 0)
            throw new IllegalAccessException("This reservation is already in use, you can't override room number");
        this.roomNumber = number;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRowOfSeat(int row) throws IllegalAccessException {
        if (this.row != 0)
            throw new IllegalAccessException("This reservation is already in use, you can't override row of seat");
        this.row = row;
    }

    public int getRowOfSeat() {
        return row;
    }

    public void setColumnOfSeat(int column) throws IllegalAccessException {
        if (this.column != 0)
            throw new IllegalAccessException("This reservation is already in use, you can't override column of seat");
        this.column = column;
    }

    public int getColumnOfSeat() {
        return column;
    }


}
