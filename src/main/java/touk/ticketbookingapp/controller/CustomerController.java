package touk.ticketbookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import touk.ticketbookingapp.entity.Customer;
import touk.ticketbookingapp.entity.Reservation;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.exception.reservation.ReservationException;
import touk.ticketbookingapp.exception.room.BookSeatException;
import touk.ticketbookingapp.service.CustomerService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService service;
    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Customer> getCustomer(
            @RequestParam String name,
            @RequestParam String surname) {
        try {
            Customer customer = service.getCustomerWithNameAndSurname(name, surname);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reservation/")
    public ResponseEntity<List<Reservation>> getReservationsOfCustomer(
            @RequestParam("name-of-customer") String nameOfCustomer,
            @RequestParam("surname-of-customer") String surnameOfCustomer) {

        try {
            List<Reservation> reservations = service.getReservationOfCustomer(
                    nameOfCustomer, surnameOfCustomer);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reservation/movie-show/{movie-show-id}/")
    public ResponseEntity<List<Reservation>> getReservationsOnMovieShowForCustomer(
            @PathVariable("movie-show-id") int movieShowId,
            @RequestParam("name-of-customer") String nameOfCustomer,
            @RequestParam("surname-of-customer") String surnameOfCustomer) {

        try {
            List<Reservation> reservations = service.getReservationsOfCustomerOnMovieShow(
                    nameOfCustomer, surnameOfCustomer, movieShowId);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/reservation/movie-show/{movie-show-id}/seat/{seat-id}/")
    public ResponseEntity<Customer> bookingSeatOnMovieShowForCustomer(
            @PathVariable("movie-show-id") int movieShowId,
            @PathVariable("seat-id") int seatId,
            @RequestBody Customer customerPrototype) {

        String name = customerPrototype.getName();
        String surname = customerPrototype.getSurname();

        try {
            service.bookingSeatOnMovieShowForCustomer(seatId, movieShowId, name, surname);
            return new ResponseEntity<>(customerPrototype, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(customerPrototype, HttpStatus.NOT_FOUND);
        } catch (BookSeatException | ReservationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(customerPrototype, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{name}/{surname}/relief/{relief-type}")
    public ResponseEntity<String> setCustomerReliefType(
            @PathVariable String name,
            @PathVariable String surname,
            @PathVariable("relief-type") String reliefType) {

        try {
            service.setCustomerReliefType(name, surname, reliefType);
            return new ResponseEntity<>(reliefType, HttpStatus.OK);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(reliefType, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("")
    public ResponseEntity<Customer> addCustomer(
            @RequestBody Customer customer) {

        try {
            service.addCustomer(customer);
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(customer, HttpStatus.CONFLICT);
        }
    }

}
