package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.exception.reservation.ReservationException;
import touk.ticketbookingapp.exception.room.BookSeatException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTest {
    private static Seat firstSeat;
    private static Seat firstSeatCopy;
    private static Seat secondSeat;
    private static MovieShow firstMovieShow;
    private static MovieShow secondMovieShow;
    private  static Customer tom;
    private static Customer jerry;

    @BeforeAll
    public static void initSeatTest() {
        createSeats();
        createCustomers();
        createMovesShows();
    }

    @Test
    public void getIdTest() {
        assertEquals(1, firstSeat.getId());
        assertEquals(2, secondSeat.getId());
    }

    @Test
    public void hasId() {
        assertTrue(firstSeat.hasId(1));
        assertTrue(secondSeat.hasId(2));
        assertFalse(firstSeat.hasId(13214));
        assertFalse(secondSeat.hasId(1));
    }

    @Test
    public void hasSameIdTest() {
        assertTrue(firstSeat.hasSameId(firstSeatCopy));
        assertFalse(firstSeat.hasSameId(secondSeat));
    }

    @Test
    public void hasReservationOnMovieShowTest() {
        try {
            Reservation tomReservation = firstMovieShow.createReservationForCustomer(tom);
            Reservation jerryReservation = secondMovieShow.createReservationForCustomer(jerry);

            firstSeat.book(tomReservation);
            secondSeat.book(jerryReservation);

            assertTrue(firstSeat.hasReservationOnMovieShow(firstMovieShow));
            assertTrue(secondSeat.hasReservationOnMovieShow(secondMovieShow));

            assertFalse(firstSeat.hasReservationOnMovieShow(secondMovieShow));
            assertFalse(secondSeat.hasReservationOnMovieShow(firstMovieShow));
        } catch (BookSeatException | ReservationException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getReservationsOfCustomerTest() {
        try {
            Reservation tomReservation = firstMovieShow.createReservationForCustomer(tom);
            Reservation jerryReservation = secondMovieShow.createReservationForCustomer(jerry);

            firstSeat.book(tomReservation);
            secondSeat.book(jerryReservation);

            Reservation firstTomReservation = firstSeat.getReservationsOfCustomer(tom).get(0);
            assertTrue(firstTomReservation.belongsToCustomer(tom));
            assertFalse(firstTomReservation.belongsToCustomer(jerry));

            Reservation firstJerryReservation = secondSeat.getReservationsOfCustomer(jerry).get(0);
            assertTrue(firstJerryReservation.belongsToCustomer(jerry));
            assertFalse(firstJerryReservation.belongsToCustomer(tom));
        } catch (BookSeatException | ReservationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createSeats() {
        firstSeat = new Seat(1, 1, 1);
        firstSeatCopy = new Seat(1, 2, 3);
        secondSeat = new Seat(2, 2, 1);
    }

    private static void createCustomers() {
        try {
            tom = new Customer("Tom", "Cat");
            jerry = new Customer("Jerry", "Mouse");
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createMovesShows() {
        createFirstMovieShow();
        createSecondMovieShow();
    }

    private static void createFirstMovieShow() {
        Movie granTorino = new Movie("Gran Torino");
        LocalDateTime start = LocalDateTime.of(2022, 4, 15, 10, 30,0);
        LocalDateTime end = LocalDateTime.of(2022, 4, 15, 15, 30,0);
        firstMovieShow = new MovieShow(1234, granTorino, start, end);
    }

    private static void createSecondMovieShow() {
        Movie leon = new Movie("Leon");
        LocalDateTime start = LocalDateTime.of(2022, 5, 16, 15, 30,0);
        LocalDateTime end = LocalDateTime.of(2022, 5, 16, 20, 30,0);
        secondMovieShow = new MovieShow(1234, leon, start, end);
    }

}
