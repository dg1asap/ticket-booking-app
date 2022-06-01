package touk.ticketbookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.ticketbookingapp.entity.Customer;
import touk.ticketbookingapp.entity.MovieShow;
import touk.ticketbookingapp.entity.Reservation;
import touk.ticketbookingapp.entity.Room;
import touk.ticketbookingapp.repository.customer.CustomerRepository;
import touk.ticketbookingapp.repository.movieShow.MovieShowRepository;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    private final MovieShowRepository movieShowRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(MovieShowRepository movieShowRepository, CustomerRepository customerRepository) {
        this.movieShowRepository = movieShowRepository;
        this.customerRepository = customerRepository;
    }

    public void bookingSeatOnMovieShowForCustomer(
            int seatId, int movieShowId, String nameOfCustomer, String surnameOfCustomer)
            throws IllegalAccessException, IllegalStateException{

        Customer customer = getCustomerWithNameAndSurname(nameOfCustomer, surnameOfCustomer);
        MovieShow movieShow = movieShowRepository.getMovieShowWithId(movieShowId);
        Room room = movieShowRepository.getRoomByMovieShowId(movieShowId);

        try {
            Reservation reservation = movieShow.createReservationForCustomer(customer);
            room.bookSeatWithId(reservation, seatId);
            customer.addFeeForMovieShow(movieShowId);
        } catch (NoSuchElementException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void addCustomer(Customer customer) {
        try {
            customerRepository.addCustomer(customer);
        } catch (NoSuchElementException e) {
            System.out.println("Customer with name " + customer.getName() + " and surname " + customer.getSurname() + " exist in database");
            throw e;
        }
    }

    public void setCustomerReliefType(String name, String surname, String reliefType) {
        Customer customer = getCustomerWithNameAndSurname(name, surname);
        customer.setRelief(reliefType);
    }

    public Customer getCustomerWithNameAndSurname(String name, String surname) {
        try {
            return customerRepository.getCustomerWithNameAndSurname(name, surname);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Cant' book seat because not found customer with name " + name + " and surname " + surname);
        }
    }

    public List<Reservation> getReservationOfCustomerOnMovieShow(String name, String surname, int movieShowId) {
        Customer customer = getCustomerWithNameAndSurname(name, surname);
        Room room = movieShowRepository.getRoomByMovieShowId(movieShowId);
        MovieShow movieShow = movieShowRepository.getMovieShowWithId(movieShowId);
        return room.getReservationsOfCustomerOnMovieShow(customer, movieShow);
    }

    public List<Reservation> getReservationOfCustomer(String name, String surname) {
        Customer customer = getCustomerWithNameAndSurname(name, surname);
        List<Room> rooms = movieShowRepository.getRooms();
        return rooms.stream()
                .map(room1 -> room1.getReservationsOfCustomer(customer))
                .flatMap(Collection::stream)
                .toList();
    }
}
