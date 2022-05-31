package touk.ticketbookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.mapper.StringToLocalDateTimeConverter;
import touk.ticketbookingapp.service.CinemaService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("movie-shows")
public class MovieShowController {
    @Autowired
    CinemaService cinemaService;

    @GetMapping("")
    public Set<MovieShow> getMovieShows() {
        return cinemaService.getMovieShows();
    }

    //example format:   7:20-1/2/2022
    @GetMapping("/period/")
    public List<MovieShow> getMovieShowsInPeriod(
            @RequestParam String from,  @RequestParam String to) {

        LocalDateTime fromLocalDateTime = StringToLocalDateTimeConverter.convert(from);
        LocalDateTime toLocalDateTime = StringToLocalDateTimeConverter.convert(to);

        return cinemaService.getSortedMovieShowsInPeriod(fromLocalDateTime, toLocalDateTime);
    }

    @GetMapping("/{id}/room")
    public Room getRoomWithMovieShowId(@PathVariable int id) {
        return cinemaService.getRoomByMovieShowId(id);
    }

    @GetMapping("/{id}/available-seats")
    public List<Seat> getAvailableSeatsOnMovieShow(@PathVariable int id) {
        return cinemaService.getAvailableSeatsOnMovieShow(id);
    }


}
