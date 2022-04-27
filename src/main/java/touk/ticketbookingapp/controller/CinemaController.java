package touk.ticketbookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.mapper.StringToCalendarConverter;
import touk.ticketbookingapp.service.CinemaService;

import java.util.*;

@RestController
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @GetMapping("/movie-shows")
    public ResponseEntity<Set<MovieShow>> getMovieShows() {
        Set<MovieShow> movieShows = cinemaService.getMovieShows();
        return new ResponseEntity<>(movieShows, HttpStatus.OK);
    }

    //example format:   7:20-1/2/2022
    @GetMapping("/movie-shows/period/")
    public ResponseEntity<List<MovieShow>> getMovieShowsInPeriod(
            @RequestParam String from,  @RequestParam String to) {

        Calendar fromCalendar = StringToCalendarConverter.convert(from);
        Calendar toCalendar = StringToCalendarConverter.convert(to);

        List<MovieShow> movieShows = cinemaService.getSortedMovieShowsInPeriod(fromCalendar, toCalendar);
        return new ResponseEntity<>(movieShows, HttpStatus.OK);
    }

    @GetMapping("/room/movie-shows/{id}")
    public ResponseEntity<Room> getRoomWithMovieShowId(@PathVariable int id) {
        Room room = cinemaService.getRoomByMovieShowId(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping("movie-shows/{id}/available-seats")
    public ResponseEntity<List<Seat>> getAvailableSeatsOnMovieShow(@PathVariable int id) {
        List <Seat> seats = cinemaService.getAvailableSeatsOnMovieShow(id);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @GetMapping("/customer/")
    public ResponseEntity<Customer> getCustomer(
            @RequestParam String name,
            @RequestParam String surname) {

        Customer customer = cinemaService.getCustomerWithNameAndSurname(name, surname);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/customer/reservation/movie-show/{id}/")
    public ResponseEntity<List<Reservation>> getReservationOfCustomerOnMovieShow(
            @RequestParam("name-of-customer") String nameOfCustomer,
            @RequestParam("surname-of-customer") String surnameOfCustomer,
            @PathVariable String id) {

        int screeningId = Integer.parseInt(id);
        List<Reservation> reservations = cinemaService.getReservationOfCustomerOnMovieShow(
                nameOfCustomer, surnameOfCustomer, screeningId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/customer/reservation/")
    public ResponseEntity<List<Reservation>> getReservationsOfCustomer(
            @RequestParam("name-of-customer") String nameOfCustomer,
            @RequestParam("surname-of-customer") String surnameOfCustomer) {

        List<Reservation> reservations = cinemaService.getReservationOfCustomer(
                nameOfCustomer, surnameOfCustomer);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PutMapping("/customer/reservation/movie-show/{movie-show-id}/seat/{seat-id}/")
    public ResponseEntity<String> bookingSeatOnMovieShowForCustomer(
            @PathVariable("movie-show-id") String movieShowIdAsString,
            @PathVariable("seat-id") String seatsIdAsString,
            @RequestBody Customer customerPrototype) {

        String name = customerPrototype.getName();
        String surname = customerPrototype.getSurname();

        int seatId = Integer.parseInt(seatsIdAsString);
        int movieShowId = Integer.parseInt(movieShowIdAsString);

        try {
            cinemaService.bookingSeatOnMovieShowForCustomer(seatId, movieShowId, name, surname);
            return new ResponseEntity<>("Booking for " + name + " " + surname + " completed successfully\n", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("404 NOT FOUND : Booking for " + name + " " + surname + " completed failed\n", HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("400 BAD REQUEST: Booking for " + name + " " + surname + " completed failed\n", HttpStatus.NOT_FOUND);
        }
}

    @PutMapping("/customer/{name}/{surname}/relief/{relief-type}")
    public ResponseEntity<String> setCustomerReliefType(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable("relief-type") String reliefType) {

        cinemaService.setCustomerReliefType(name, surname, reliefType);
        return new ResponseEntity<>(reliefType, HttpStatus.OK);
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> addCustomer(
            @RequestBody Customer customer) {

        cinemaService.addCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }


}
