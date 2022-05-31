package touk.ticketbookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import touk.ticketbookingapp.entity.Customer;
import touk.ticketbookingapp.entity.Reservation;
import touk.ticketbookingapp.service.CinemaService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CinemaService cinemaService;

    @GetMapping("/")
    public Customer getCustomer(
            @RequestParam String name,
            @RequestParam String surname) {
        return cinemaService.getCustomerWithNameAndSurname(name, surname);
    }

    @GetMapping("/reservation/movie-show/{movie-show-id}/")
    public List<Reservation> getReservationOnMovieShowForCustomer(
            @PathVariable("movie-show-id") int movieShowId,
            @RequestParam("name-of-customer") String nameOfCustomer,
            @RequestParam("surname-of-customer") String surnameOfCustomer) {

        return cinemaService.getReservationOfCustomerOnMovieShow(
                nameOfCustomer, surnameOfCustomer, movieShowId);
    }

    @GetMapping("/reservation/")
    public List<Reservation> getReservationsOfCustomer(
            @RequestParam("name-of-customer") String nameOfCustomer,
            @RequestParam("surname-of-customer") String surnameOfCustomer) {

        return cinemaService.getReservationOfCustomer(
                nameOfCustomer, surnameOfCustomer);
    }

    @PutMapping("/reservation/movie-show/{movie-show-id}/seat/{seat-id}/")
    public ResponseEntity<Customer> bookingSeatOnMovieShowForCustomer(
            @PathVariable("movie-show-id") int movieShowId,
            @PathVariable("seat-id") int seatId,
            @RequestBody Customer customerPrototype) {

        String name = customerPrototype.getName();
        String surname = customerPrototype.getSurname();

        try {
            cinemaService.bookingSeatOnMovieShowForCustomer(seatId, movieShowId, name, surname);
            return new ResponseEntity<>(customerPrototype, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(customerPrototype, HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(customerPrototype, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{name}/{surname}/relief/{relief-type}")
    public String setCustomerReliefType(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable("relief-type") String reliefType) {

        cinemaService.setCustomerReliefType(name, surname, reliefType);
        return reliefType;
    }

    @PostMapping("")
    public ResponseEntity<Customer> addCustomer(
            @RequestBody Customer customer) {

        try {
            cinemaService.addCustomer(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(customer, HttpStatus.CONFLICT);
        }
    }

}
