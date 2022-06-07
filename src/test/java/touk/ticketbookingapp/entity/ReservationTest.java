package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.exception.reservation.ReservationException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    private static LocalDateTime startOfFirstReservation;
    private static LocalDateTime endOfFirstReservation;
    private static LocalDateTime startOfSecondReservation;
    private static LocalDateTime endOfSecondReservation;
    private static Customer tom;
    private static Customer eva;
    private static Reservation firstReservation;
    private static Reservation secondReservation;
    private static Reservation overlappingReservation;

    @BeforeAll
    public static void createReservations() {
        try {
            tom = new Customer("Tom", "Smith");
            eva = new Customer("Eva", "Polka");
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }

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
    public void isOverlappingWithLocalDateTimeTest() {
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
    public void getAndSetRoomNumberTest() {
        try {
            firstReservation.setRoomNumber(1);
            secondReservation.setRoomNumber(13);
        } catch (ReservationException e) {
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
        } catch (ReservationException e) {
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
        } catch (ReservationException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(11, firstReservation.getColumnOfSeat());
        assertEquals(3, secondReservation.getColumnOfSeat());
    }

    private static void createFirstReservationFor(Customer customer) {
        startOfFirstReservation = LocalDateTime.of(2022, 5, 20, 15, 0);
        endOfFirstReservation = LocalDateTime.of(2022, 5, 20, 18, 0);

        firstReservation = new Reservation(startOfFirstReservation, endOfFirstReservation, customer);
    }

    private static void createSecondReservationFor(Customer customer) {
        startOfSecondReservation = LocalDateTime.of(2022, 7, 20, 12, 0);
        endOfSecondReservation = LocalDateTime.of(2022, 7, 20, 15, 0);

        secondReservation = new Reservation(startOfSecondReservation, endOfSecondReservation, customer);
    }

    private static void createOverlappingReservation(Customer customer) {
        LocalDateTime start = LocalDateTime.of(1990, 1, 1, 1, 0);
        LocalDateTime end = LocalDateTime.of(2030, 1, 1, 1, 0);

        overlappingReservation = new Reservation(start, end, customer);
    }

}
