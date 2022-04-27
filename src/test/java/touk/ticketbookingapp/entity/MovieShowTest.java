package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

        Calendar firstMovieStart = new GregorianCalendar();
        Calendar firstMovieEnd = new GregorianCalendar();
        firstMovieStart.set(2022, 4, 15, 15, 30);
        firstMovieEnd.set(2022, 4, 15, 19, 30);
        firstMovieShow = new MovieShow(1234, granTorino, firstMovieStart, firstMovieEnd);

        Calendar secondMovieStart = new GregorianCalendar();
        Calendar secondMovieEnd = new GregorianCalendar();
        secondMovieStart.set(2022, 4, 15, 20, 15);
        secondMovieEnd.set(2022, 4, 15, 23, 15);
        secondMovieShow = new MovieShow(1235, granTorino, secondMovieStart, secondMovieEnd);

        Calendar thirdMovieStart = new GregorianCalendar();
        Calendar thirdMovieEnd = new GregorianCalendar();
        thirdMovieStart.set(2023, 10, 30, 10, 0);
        thirdMovieEnd.set(2023, 10, 30, 20, 0);
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
    public void getDayOfStartAsStringTest() {
        assertEquals("15/4/2022", firstMovieShow.getDayOfStartAsString());
        assertEquals("15/4/2022", secondMovieShow.getDayOfStartAsString());
        assertEquals("30/10/2023", thirdMovieShow.getDayOfStartAsString());
    }

    @Test
    public void getTimeOfStartAsStringTest() {
        assertEquals("15:30", firstMovieShow.getTimeOfStartAsString());
        assertEquals("20:15", secondMovieShow.getTimeOfStartAsString());
        assertEquals("10:00", thirdMovieShow.getTimeOfStartAsString());
    }

    @Test
    public void getDayOfEndAsStringTest() {
        assertEquals("15/4/2022", firstMovieShow.getDayOfEndAsString());
        assertEquals("15/4/2022", secondMovieShow.getDayOfEndAsString());
        assertEquals("30/10/2023", thirdMovieShow.getDayOfEndAsString());
    }

    @Test
    public void getTimeOfEndAsStringTest() {
        assertEquals("19:30", firstMovieShow.getTimeOfEndAsString());
        assertEquals("23:15", secondMovieShow.getTimeOfEndAsString());
        assertEquals("20:00", thirdMovieShow.getTimeOfEndAsString());
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
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void isInOverlappingWithCalendarTest() {
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
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean is15April2022(MovieShow movieShow) {
        Calendar start = new GregorianCalendar();
        start.set(2022, 4, 15, 0, 0);

        Calendar end = new GregorianCalendar();
        end.set(2022, 4, 15, 23, 59);

        return movieShow.isOverlappingWith(start, end);
    }

    private boolean isIn2023(MovieShow movieShow) {
        Calendar start = new GregorianCalendar();
        start.set(2023, 1, 1, 0, 0);

        Calendar end = new GregorianCalendar();
        end.set(2023, 12, 31, 23, 59);

        return movieShow.isOverlappingWith(start, end);
    }
}
