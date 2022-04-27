package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    private static Calendar startOfFirstReservation;
    private static Calendar endOfFirstReservation;
    private static Calendar startOfSecondReservation;
    private static Calendar endOfSecondReservation;
    private static Customer tom;
    private static Customer eva;
    private static Reservation firstReservation;
    private static Reservation secondReservation;
    private static Reservation overlappingReservation;

    @BeforeAll
    public static void createReservations() {
        tom = new Customer("Tom", "Smith");
        eva = new Customer("Eva", "Polka");

        createFirstReservationFor(tom);
        createSecondReservationFor(eva);
        createOverlappingReservation(tom);
    }

    @Test
    public void isOverlappingWithReservationTest() {
        assertTrue(firstReservation.isOverlappingWith(firstReservation));
        assertFalse(secondReservation.isOverlappingWith(firstReservation));
        assertTrue(firstReservation.isOverlappingWith(overlappingReservation));
        assertTrue(secondReservation.isOverlappingWith(overlappingReservation));
    }

    @Test
    public void isOverlappingWithCalendarTest() {
        assertTrue(firstReservation.isOverlappingWith(startOfFirstReservation, endOfFirstReservation));
        assertTrue(firstReservation.isOverlappingWith(startOfFirstReservation, endOfSecondReservation));
        assertFalse(firstReservation.isOverlappingWith(startOfSecondReservation,endOfSecondReservation));
        assertTrue(secondReservation.isOverlappingWith(startOfSecondReservation, endOfSecondReservation));
        assertFalse(secondReservation.isOverlappingWith(startOfFirstReservation, endOfFirstReservation));
    }

    @Test
    public void belongsToCustomerTest() {
        assertTrue(firstReservation.belongsToCustomer(tom));
        assertTrue(secondReservation.belongsToCustomer(eva));
        assertFalse(firstReservation.belongsToCustomer(eva));
        assertFalse(secondReservation.belongsToCustomer(tom));
    }

    @Test
    public void getDayOfStartAsStringTest() {
        assertEquals("20/5/2022", firstReservation.getDayOfStartAsString());
        assertEquals("20/7/2022", secondReservation.getDayOfStartAsString());
    }

    @Test
    public void getTimeOfStartAsStringTest() {
        assertEquals("15:00", firstReservation.getTimeOfStartAsString());
        assertEquals("12:00", secondReservation.getTimeOfStartAsString());
    }

    @Test
    public void getDayOfEndAsStringTest() {
        assertEquals("20/5/2022", firstReservation.getDayOfEndAsString());
        assertEquals("20/7/2022", secondReservation.getDayOfEndAsString());
    }

    @Test
    public void getTimeOfEndAsStringTest() {
        assertEquals("18:00", firstReservation.getTimeOfEndAsString());
        assertEquals("15:00", secondReservation.getTimeOfEndAsString());
    }

    @Test
    public void getAndSetRoomNumberTest() {
        try {
            firstReservation.setRoomNumber(1);
            secondReservation.setRoomNumber(13);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(1, firstReservation.getRoomNumber());
        assertEquals(13, secondReservation.getRoomNumber());
    }

    @Test
    public void getAndSetRowOfSeat() {
        try {
            firstReservation.setRowOfSeat(12);
            secondReservation.setRowOfSeat(2);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(12, firstReservation.getRowOfSeat());
        assertEquals(2, secondReservation.getRowOfSeat());
    }

    @Test
    public void getAndSetColumnOfSeat() {
        try {
            firstReservation.setColumnOfSeat(11);
            secondReservation.setColumnOfSeat(3);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(11, firstReservation.getColumnOfSeat());
        assertEquals(3, secondReservation.getColumnOfSeat());
    }

    private static void createFirstReservationFor(Customer customer) {
        startOfFirstReservation = new GregorianCalendar();
        startOfFirstReservation.set(2022, 5, 20, 15, 0);

        endOfFirstReservation = new GregorianCalendar();
        endOfFirstReservation.set(2022, 5, 20, 18, 0);

        firstReservation = new Reservation(startOfFirstReservation, endOfFirstReservation, customer);
    }

    private static void createSecondReservationFor(Customer customer) {
        startOfSecondReservation = new GregorianCalendar();
        startOfSecondReservation.set(2022, 7, 20, 12, 0);

        endOfSecondReservation = new GregorianCalendar();
        endOfSecondReservation.set(2022, 7, 20, 15, 0);

        secondReservation = new Reservation(startOfSecondReservation, endOfSecondReservation, customer);
    }

    private static void createOverlappingReservation(Customer customer) {
        Calendar start = new GregorianCalendar();
        start.set(1990, 1, 1, 1, 0);

        Calendar end = new GregorianCalendar();
        end.set(2030, 1, 1, 1, 0);

        overlappingReservation = new Reservation(start, end, customer);
    }

}
