package touk.ticketbookingapp.repository.movieShow;

import org.springframework.stereotype.Repository;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.util.Collector;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class LocalMovieShowRepository implements MovieShowRepository {
    private final Map<MovieShow, Room> movieShowRoomMap;

    public LocalMovieShowRepository() {
        movieShowRoomMap = new HashMap<>();
        MovieShowRepositoryInitializer.init(this);
    }

    @Override
    public void addMovieShowAndRoom(MovieShow movieShow, Room room) {
        movieShowRoomMap.put(movieShow, room);
    }

    @Override
    public MovieShow getMovieShowWithId(int id) {
        try {
            return movieShowRoomMap.keySet().stream()
                    .filter(movieShow -> movieShow.hasId(id))
                    .collect(Collector.toSingleton());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Movie show with id " + id + " not found");
        }
    }

    @Override
    public Set<MovieShow> getMovieShows() {
        if (movieShowRoomMap.size() == 0)
            throw new NoSuchElementException("Movie show database is clear");
        return movieShowRoomMap.keySet();
    }

    @Override
    public List<MovieShow> getSortedMovieShowsBetween(LocalDateTime from, LocalDateTime to) {
        List<MovieShow> movieShows = getMovieShowsInPeriod(from, to);
        return sortMovieShowsByTitleAndStart(movieShows);
    }

    @Override
    public Room getRoomWithId(int roomNumber) {
        try {
            return movieShowRoomMap.values().stream()
                    .filter(room -> room.hasNumber(roomNumber))
                    .collect(Collector.toSingleton());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Room with number " + roomNumber + " not found or found more than one");
        }
    }

    @Override
    public Room getRoomByMovieShowId(int id) throws NoSuchElementException {
        try {
            MovieShow movieShow = getMovieShowWithId(id);
            return movieShowRoomMap.get(movieShow);
        } catch(NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Not found room by movie show id " + id);
        }
    }

    @Override
    public List<Room> getRooms() {
        return movieShowRoomMap.values().stream().toList();
    }

    private List<MovieShow> getMovieShowsInPeriod(LocalDateTime from, LocalDateTime to) {
        return movieShowRoomMap.keySet().stream()
                .filter(movieShow -> movieShow.isOverlappingWith(from, to))
                .toList();
    }

    private List<MovieShow> sortMovieShowsByTitleAndStart(List<MovieShow> unmodifiableMovieShows) {
        List<MovieShow> modifiableMovieShows = new ArrayList<>(unmodifiableMovieShows);
        modifiableMovieShows.sort(new MovieShowSortingComparator());
        return modifiableMovieShows;
    }

}
