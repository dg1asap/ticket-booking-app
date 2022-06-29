package touk.ticketbookingapp.repository.movieShow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import touk.ticketbookingapp.entity.MovieShow;
import touk.ticketbookingapp.entity.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

//@Repository
public class MovieShowMySQLRepository implements MovieShowRepository {
    private JdbcTemplate jdbcTemplate;

    //@Autowired
    public MovieShowMySQLRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        jdbcTemplate.update("SET SQL_SAFE_UPDATES = 0;");

        jdbcTemplate.update("DELETE FROM room;");
        jdbcTemplate.update("DELETE FROM seat;");
        jdbcTemplate.update("DELETE FROM movie;");
        jdbcTemplate.update("DELETE FROM movieshow;");

        jdbcTemplate.update("INSERT INTO room(id, number) VALUES(1, 1), (2, 2), (3, 3), (4, 4);");

        jdbcTemplate.update("INSERT INTO seat(room_id, seat_row, seat_column) VALUES(1, 3, 4);");
        jdbcTemplate.update("INSERT INTO movie(id, tittle) VALUES(1, 'Fight Club'), (2, 'Gran Torino'), (3, 'Batman'), (4, 'Seven'), (5, 'Truman Show');");
        jdbcTemplate.update("INSERT INTO movieshow(room_id, movie_id, start, end) VALUES " +
                "(1, 1, '2022-1-1 6:0:0', '2022-1-1 10:0:0'), (1, 5, '2022-1-1 12:0:0', '2022-1-1 14:20:0'), (1, 3, '2022-1-1 16:15:0', '2022-1-1 19:20:0'), " +
                "(2, 1, '2022-1-1 21:20:0', '2022-1-1 23:30:0'), (2, 4, '2022-5-27 10:0:0', '2022-5-27 12:0:0'), (2, 5, '2022-5-27 15:20:0', '2022-5-27 18:15:0'), " +
                "(3, 3, '2022-5-27 20:0:0', '2022-5-27 22:0:0'), (3, 2, '2022-10-28 9:0:0', '2022-10-28 11:30:0'), " +
                "(4, 4, '2022-10-28 17:10:0', '2022-10-28 18:10:0'), (4, 2, '2022-10-28 20:0:0', '2022-10-28 21:0:0');");
        jdbcTemplate.update("");
    }


    @Override
    public void addMovieShowAndRoom(MovieShow movieShow, Room room) {

    }

    @Override
    public MovieShow getMovieShowWithId(int id) {
        return null;
    }

    @Override
    public Set<MovieShow> getMovieShows() {
        return null;
    }

    @Override
    public List<MovieShow> getSortedMovieShowsBetween(LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public Room getRoomWithId(int roomNumber) {
        return null;
    }

    @Override
    public Room getRoomByMovieShowId(int id) throws NoSuchElementException {
        return null;
    }

    @Override
    public List<Room> getRooms() {
        return null;
    }
}
