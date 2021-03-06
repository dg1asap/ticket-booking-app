package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.exception.room.BookSeatException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MovieShowTest {
    static private MovieShow firstMovieShow;
    static private MovieShow secondMovieShow;
    static private MovieShow thirdMovieShow;
    static private Movie granTorino;
    static private Movie grandFather;

    @BeforeAll
    public static void createMovesShow() {
        granTorino = new Movie("Gran Torino");
        grandFather = new Movie("Grandfather");

        LocalDateTime firstMovieStart = LocalDateTime.of(2022, 4, 15, 15, 30);
        LocalDateTime firstMovieEnd = LocalDateTime.of(2022, 4, 15, 19, 30);
        firstMovieShow = new MovieShow(1234, granTorino, firstMovieStart, firstMovieEnd);

        LocalDateTime secondMovieStart = LocalDateTime.of(2022, 4, 15, 20, 15);
        LocalDateTime secondMovieEnd = LocalDateTime.of(2022, 4, 15, 23, 15);
        secondMovieShow = new MovieShow(1235, granTorino, secondMovieStart, secondMovieEnd);

        LocalDateTime thirdMovieStart = LocalDateTime.of(2023, 10, 30, 10, 0);
        LocalDateTime thirdMovieEnd = LocalDateTime.of(2023, 10, 30, 20, 0);
        thirdMovieShow = new MovieShow(9998, grandFather, thirdMovieStart, thirdMovieEnd);
    }

    @Test
    public void getIdTest() {
        assertEquals(1234, firstMovieShow.getId());
        assertEquals(1235, secondMovieShow.getId());
        assertEquals(9998, thirdMovieShow.getId());
    }

    @Test
    public void hasIdTest() {
        assertTrue(firstMovieShow.hasId(1234));
        assertTrue(secondMovieShow.hasId(1235));
        assertTrue(thirdMovieShow.hasId(9998));

        assertFalse(firstMovieShow.hasId(11));
        assertFalse(secondMovieShow.hasId(3124));
        assertFalse(thirdMovieShow.hasId(9999));
    }

    @Test
    public void getMovieTest() {
        assertEquals(granTorino, firstMovieShow.getMovie());
        assertEquals(granTorino, secondMovieShow.getMovie());
        assertEquals(grandFather, thirdMovieShow.getMovie());
    }

    @Test
    public void createReservationForCustomerTest() {
        try {
            Customer tom = new Customer("Tom", "Smith");
            Customer eva = new Customer("Eva", "Polka");

            Reservation tomReservation = firstMovieShow.createReservationForCustomer(tom);
            Reservation evaReservation = secondMovieShow.createReservationForCustomer(eva);
            Reservation evaSecondReservation = thirdMovieShow.createReservationForCustomer(eva);

            assertTrue(tomReservation.belongsToCustomer(tom));
            assertTrue(evaReservation.belongsToCustomer(eva));
            assertTrue(evaSecondReservation.belongsToCustomer(eva));

            assertFalse(evaReservation.belongsToCustomer(tom));
        } catch (CustomerException | BookSeatException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void isInOverlappingWithLocalDateTimeTest() {
        assertTrue(is15April2022(firstMovieShow));
        assertTrue(is15April2022(secondMovieShow));
        assertFalse(is15April2022(thirdMovieShow));

        assertFalse(isIn2023(firstMovieShow));
        assertFalse(isIn2023(secondMovieShow));
        assertTrue(isIn2023(thirdMovieShow));
    }

    @Test
    public void isOverlappingWithReservationTest() {
        try {
            Customer tom = new Customer("Tom", "Smith");
            Customer eva = new Customer("Eva", "Polka");

            Reservation tomReservation = firstMovieShow.createReservationForCustomer(tom);
            Reservation evaReservation = secondMovieShow.createReservationForCustomer(eva);

            assertTrue(firstMovieShow.isOverlappingWith(tomReservation));
            assertFalse(secondMovieShow.isOverlappingWith(tomReservation));
            assertFalse(thirdMovieShow.isOverlappingWith(tomReservation));

            assertFalse(firstMovieShow.isOverlappingWith(evaReservation));
            assertTrue(secondMovieShow.isOverlappingWith(evaReservation));
            assertFalse(thirdMovieShow.isOverlappingWith(evaReservation));
        } catch (BookSeatException | CustomerException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean is15April2022(MovieShow movieShow) {
        LocalDateTime start = LocalDateTime.of(2022, 4, 15, 0, 0);
        LocalDateTime end = LocalDateTime.of(2022, 4, 15, 23, 59);

        return movieShow.isOverlappingWith(start, end);
    }

    private boolean isIn2023(MovieShow movieShow) {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

        return movieShow.isOverlappingWith(start, end);
    }
}
