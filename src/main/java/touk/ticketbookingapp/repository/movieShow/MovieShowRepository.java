package touk.ticketbookingapp.repository.movieShow;

import touk.ticketbookingapp.entity.*;

import java.time.LocalDateTime;
import java.util.*;

public interface MovieShowRepository {
    void addMovieShowAndRoom(MovieShow movieShow, Room room);
    MovieShow getMovieShowWithId(int id);
    Set<MovieShow> getMovieShows();
    List<MovieShow> getSortedMovieShowsBetween(LocalDateTime from, LocalDateTime to);
    Room getRoomWithId(int roomNumber);
    Room getRoomByMovieShowId(int id) throws NoSuchElementException;
    List<Room> getRooms();

}
