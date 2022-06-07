package touk.ticketbookingapp.service;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.exception.reservation.ReservationException;
import touk.ticketbookingapp.exception.room.BookSeatException;
import touk.ticketbookingapp.repository.customer.CustomerRepository;
import touk.ticketbookingapp.repository.movieShow.MovieShowRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    private final MovieShowRepository movieShowRepository;
    private final CustomerRepository customerRepository;
    private Checkout checkout;

    @Autowired
    public CustomerService(MovieShowRepository movieShowRepository, CustomerRepository customerRepository) {
        this.movieShowRepository = movieShowRepository;
        this.customerRepository = customerRepository;
        createTicketOffice();
    }

    public void bookingSeatOnMovieShowForCustomer (
            int seatId, int movieShowId, String nameOfCustomer, String surnameOfCustomer)
            throws NoSuchElementException, BookSeatException, ReservationException {

        try {
            MovieShow movieShow = movieShowRepository.getMovieShowWithId(movieShowId);
            Room room = movieShowRepository.getRoomByMovieShowId(movieShowId);
            Customer customer = getCustomerWithNameAndSurname(nameOfCustomer, surnameOfCustomer);

            String reliefType = customer.getReliefType();
            Money fee = checkout.getTicketAmountInPLN(reliefType);

            Reservation reservation = movieShow.createReservationForCustomer(customer);
            room.bookSeatWithId(reservation, seatId);
            customer.addFeeForMovieShow(movieShowId, fee);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't book seat with id " + seatId + " on moveShow with id " + movieShowId + " for customer " + nameOfCustomer + " " + surnameOfCustomer + " because not found");
        } catch (BookSeatException e) {
            System.out.println(e.getMessage());
            throw new BookSeatException("Can't book seat with id " + seatId + " on moveShow with id " + movieShowId + " for customer " + nameOfCustomer + " " + surnameOfCustomer + " because you don't have proper permission");
        } catch (ReservationException e) {
            System.out.println(e.getMessage());
            throw new BookSeatException("Can't book seat with id " + seatId + " on moveShow with id " + movieShowId + " for customer " + nameOfCustomer + " " + surnameOfCustomer + " because is invalid reservation");
        }
    }

    public void addCustomer(Customer customer) throws CustomerException {
        try {
            CustomerValidator.validate(customer);
            customerRepository.addCustomer(customer);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            String name = customer.getName();
            String surname = customer.getSurname();
            throw new CustomerException("Can't add customer with name " + name + " and surname " + surname);
        }
    }

    public void setCustomerReliefType(String name, String surname, String reliefType) {
        if (!checkout.hasReliefType(reliefType))
            throw new IllegalArgumentException("Can't set customer " + name + " " + surname + " relief type because relief type " + reliefType + " don't exist");

        try {
            Customer customer = getCustomerWithNameAndSurname(name, surname);
            customer.setRelief(reliefType);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't set customer relief type because not found customer with name " + name + " and surname " + surname);
        }
    }

    public Customer getCustomerWithNameAndSurname(String name, String surname) {
        try {
            return customerRepository.getCustomerWithNameAndSurname(name, surname);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Cant' book seat because not found customer with name " + name + " and surname " + surname);
        }
    }

    public List<Reservation> getReservationsOfCustomerOnMovieShow(String name, String surname, int movieShowId) {
        try {
            Customer customer = getCustomerWithNameAndSurname(name, surname);
            Room room = movieShowRepository.getRoomByMovieShowId(movieShowId);
            MovieShow movieShow = movieShowRepository.getMovieShowWithId(movieShowId);
            return room.getReservationsOfCustomerOnMovieShow(customer, movieShow);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't get reservations of customer " + name + " " + surname + " on move-show with id " + movieShowId);
        }
    }

    public List<Reservation> getReservationOfCustomer(String name, String surname) {
        try {
            Customer customer = getCustomerWithNameAndSurname(name, surname);
            List<Room> rooms = movieShowRepository.getRooms();
            return rooms.stream()
                    .map(room1 -> room1.getReservationsOfCustomer(customer))
                    .flatMap(Collection::stream)
                    .toList();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't get reservations of customer " + name + " " + surname);
        }
    }

    private void createTicketOffice() {
        HashMap<String, Money> ticketTypeFees = new HashMap<>();
        ticketTypeFees.put("adult", Money.of(25.00, "PLN"));
        ticketTypeFees.put("student", Money.of(18.00, "PLN"));
        ticketTypeFees.put("child", Money.of(12.50, "PLN"));
        this.checkout = new Checkout(ticketTypeFees);
    }

}
