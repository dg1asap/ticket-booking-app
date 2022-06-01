package touk.ticketbookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.repository.movieShow.MovieShowRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MovieShowService {
    MovieShowRepository repository;

    @Autowired
    public MovieShowService(MovieShowRepository repository) {
        this.repository = repository;
    }

    public Set<MovieShow> getMovieShows() {
        return repository.getMovieShows();
    }

    public List<MovieShow> getSortedMovieShowsInPeriod(LocalDateTime fromCalendar, LocalDateTime toCalendar) {
        return repository.getSortedMovieShowsInPeriod(fromCalendar, toCalendar);
    }

    public Room getRoomByMovieShowId(int id) throws NoSuchElementException {
        return repository.getRoomByMovieShowId(id);
    }

    public List<Seat> getAvailableSeatsOnMovieShow(int movieShowId) {
        MovieShow movieShow = repository.getMovieShowWithId(movieShowId);
        Room room = repository.getRoomByMovieShowId(movieShowId);

        return room.getAvailableSeatsOnMovieShow(movieShow);
    }


}
