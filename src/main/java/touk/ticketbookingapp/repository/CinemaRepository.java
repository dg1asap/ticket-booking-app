package touk.ticketbookingapp.repository;

import org.springframework.stereotype.Repository;
import touk.ticketbookingapp.entity.*;

import java.time.LocalDateTime;
import java.util.*;

public interface CinemaRepository {
    Set<MovieShow> getMovieShows();
    List<Room> getRooms();
    List<MovieShow> getSortedMovieShowsInPeriod(LocalDateTime from, LocalDateTime to);
    Room getRoomByMovieShowId(int id);
    Room getRoomWithId(int roomNumber);
    void addCustomer(Customer customer);
    Customer getCustomerWithNameAndSurname(String name, String surname);
    MovieShow getMovieShowWithId(int id);

}
