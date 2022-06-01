package touk.ticketbookingapp.repository.movieShow;

import touk.ticketbookingapp.entity.*;

import java.time.LocalDateTime;
import java.util.*;

public interface MovieShowRepository {
    void addMovieShowAndRoom(MovieShow movieShow, Room room);
    Set<MovieShow> getMovieShows();
    List<Room> getRooms();
    List<MovieShow> getSortedMovieShowsInPeriod(LocalDateTime from, LocalDateTime to);
    Room getRoomByMovieShowId(int id) throws IllegalStateException;
    Room getRoomWithId(int roomNumber);
    MovieShow getMovieShowWithId(int id);


}
