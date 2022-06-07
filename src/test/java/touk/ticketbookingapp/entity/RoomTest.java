package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.exception.reservation.ReservationException;
import touk.ticketbookingapp.exception.room.BookSeatException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    private static Room firstRoom;
    private static Room secondRoom;
    private static MovieShow earlierMovieShow;
    private static MovieShow laterMovieShow;

    @BeforeAll
    public static void createRoomsAndMoviesShow() {
        createRooms();
        createMoviesShow();
    }

    private static void createRooms() {
        firstRoom = new Room(1);
        secondRoom = new Room(2);
        addSeatsToFirstRoom();
        addSeatsToSecondRoom();
    }

    private static void createMoviesShow() {
        Movie granTorino = new Movie("Gran Torino");

        LocalDateTime firstMovieStart = LocalDateTime.of(2023, 4, 15, 10, 30,0);
        LocalDateTime firstMovieEnd = LocalDateTime.of(2023, 4, 15, 15, 30,0);
        earlierMovieShow = new MovieShow(1234, granTorino, firstMovieStart, firstMovieEnd);

        LocalDateTime secondMovieStart = LocalDateTime.of(2023, 4, 15, 18, 15,0);
        LocalDateTime secondMovieEnd = LocalDateTime.of(2023, 4, 15, 20, 15,0);
        laterMovieShow = new MovieShow(1235, granTorino, secondMovieStart, secondMovieEnd);
    }

    private static void addSeatsToFirstRoom() {
        for (int i = 200; i < 300; i++)
            firstRoom.addSeat(new Seat(i,i-200,i-200));
    }

    private static void addSeatsToSecondRoom() {
        for (int i = 990000; i < 990200; i++)
            secondRoom.addSeat(new Seat(i,i-989999,i-989999));
    }

    @Test
    public void getNumberTest() {
        assertEquals(1, firstRoom.getNumber());
        assertEquals(2, secondRoom.getNumber());
    }

    @Test
    public void hasNumberTest() {
        assertTrue(firstRoom.hasNumber(1));
        assertTrue(secondRoom.hasNumber(2));

        assertFalse(firstRoom.hasNumber(13214));
        assertFalse(secondRoom.hasNumber(21324));
    }

    @Test
    public void getAvailableSeatsAndBookSeatTest() {
        assertEquals(100, getNumberOfAvailableSeatsInRoomOnMovieShow(firstRoom, earlierMovieShow));
        assertEquals(200, getNumberOfAvailableSeatsInRoomOnMovieShow(secondRoom, earlierMovieShow));
        assertEquals(100, getNumberOfAvailableSeatsInRoomOnMovieShow(firstRoom, laterMovieShow));
        assertEquals(200, getNumberOfAvailableSeatsInRoomOnMovieShow(secondRoom, laterMovieShow));

        try {
            Customer mark = new Customer("Mark", "Smith");
            Customer eva = new Customer("Eva", "Polka");
            Customer mike = new Customer("Mike", "Tyson");

            bookNSeatsInRoomOnMovieShowForCustomer(80, firstRoom, earlierMovieShow, mark);
            bookNSeatsInRoomOnMovieShowForCustomer(50, secondRoom, earlierMovieShow, eva);
            bookNSeatsInRoomOnMovieShowForCustomer(7, firstRoom, laterMovieShow, mike);
            bookNSeatsInRoomOnMovieShowForCustomer(90, secondRoom, laterMovieShow, mike);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(150, getNumberOfAvailableSeatsInRoomOnMovieShow(secondRoom, earlierMovieShow));
        assertEquals(93, getNumberOfAvailableSeatsInRoomOnMovieShow(firstRoom, laterMovieShow));
        assertEquals(110, getNumberOfAvailableSeatsInRoomOnMovieShow(secondRoom, laterMovieShow));
    }

    private int getNumberOfAvailableSeatsInRoomOnMovieShow(Room room, MovieShow movieShow) {
        List<Seat> availableSeats = room.getAvailableSeatsOnMovieShow(movieShow);
        return availableSeats.size();
    }

    private void bookNSeatsInRoomOnMovieShowForCustomer(
            int numberOfSeats, Room room, MovieShow movieShow, Customer customer) {

        try {
            List<Seat> availableSeats = room.getAvailableSeatsOnMovieShow(movieShow);
            for (int i = 0; i < numberOfSeats; i++) {
                Reservation reservation = movieShow.createReservationForCustomer(customer);
                Seat seat = availableSeats.get(i);
                int seatID = seat.getId();
                room.bookSeatWithId(reservation, seatID);
            }
        } catch (BookSeatException | ReservationException e) {
            System.out.println(e.getMessage());
        }
    }

}
