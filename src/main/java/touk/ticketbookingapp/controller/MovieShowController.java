package touk.ticketbookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.mapper.StringToLocalDateTimeConverter;
import touk.ticketbookingapp.service.MovieShowService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("movie-shows")
public class MovieShowController {
    private MovieShowService service;

    @Autowired
    public MovieShowController(MovieShowService service) {
        this.service = service;
    }

    @GetMapping("")
    public Set<MovieShow> getMovieShows() {
        return service.getMovieShows();
    }

    //example format:   7:20-1/2/2022
    @GetMapping("/period/")
    public List<MovieShow> getMovieShowsInPeriod(
            @RequestParam String from,  @RequestParam String to) {

        LocalDateTime fromLocalDateTime = StringToLocalDateTimeConverter.convert(from);
        LocalDateTime toLocalDateTime = StringToLocalDateTimeConverter.convert(to);

        return service.getSortedMovieShowsInPeriod(fromLocalDateTime, toLocalDateTime);
    }

    @GetMapping("/{id}/room")
    public ResponseEntity<Room> getRoomWithMovieShowId(@PathVariable int id) {
        try {
            Room room = service.getRoomByMovieShowId(id);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/available-seats")
    public List<Seat> getAvailableSeatsOnMovieShow(@PathVariable int id) {
        return service.getAvailableSeatsOnMovieShow(id);
    }


}
