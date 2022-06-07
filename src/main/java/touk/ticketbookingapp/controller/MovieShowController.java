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
    public ResponseEntity<Set<MovieShow>> getMovieShows() {
        try {
            Set<MovieShow> movieShows = service.getMovieShows();
            return new ResponseEntity<>(movieShows, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // TODO consider ISO format
    //example format:   7:20-1/2/2022
    @GetMapping("/")
    public ResponseEntity<List<MovieShow>> getMovieShowsBetween (
            @RequestParam("from") String fromAsString,  @RequestParam("to") String toAsString) {

        try {
            LocalDateTime form = StringToLocalDateTimeConverter.convert(fromAsString);
            LocalDateTime to = StringToLocalDateTimeConverter.convert(toAsString);
            List<MovieShow> movieShows = service.getSortedMovieShowsBetween(form, to);
            return new ResponseEntity<>(movieShows, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<List<Seat>> getAvailableSeatsOnMovieShow(@PathVariable int id) {
        try {
            List<Seat> seats = service.getAvailableSeatsOnMovieShow(id);
            return new ResponseEntity<>(seats, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
