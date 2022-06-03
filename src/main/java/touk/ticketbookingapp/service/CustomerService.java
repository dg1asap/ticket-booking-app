package touk.ticketbookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.exception.customer.CustomerException;
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

        try {
            Customer customer = getCustomerWithNameAndSurname(nameOfCustomer, surnameOfCustomer);
            MovieShow movieShow = movieShowRepository.getMovieShowWithId(movieShowId);
            Room room = movieShowRepository.getRoomByMovieShowId(movieShowId);

            Reservation reservation = movieShow.createReservationForCustomer(customer);
            room.bookSeatWithId(reservation, seatId);
            customer.addFeeForMovieShow(movieShowId);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't booking seat with id " + seatId + " on moveShow with id " + movieShowId + " for customer " + nameOfCustomer + " " + surnameOfCustomer + " because not found");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            throw new IllegalAccessException("Can't booking seat with id " + seatId + " on moveShow with id " + movieShowId + " for customer " + nameOfCustomer + " " + surnameOfCustomer + " because you don't have proper permission");
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

    public void setCustomerReliefType(String name, String surname, String reliefType) throws IllegalAccessException {
        try {
            Customer customer = getCustomerWithNameAndSurname(name, surname);
            customer.setRelief(reliefType);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Can't set customer relief type because not found customer with name " + name + " and surname " + surname);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            throw new IllegalAccessException("Can't set customer " + name + " " + surname + " relief type because " + name + " " + surname + " have not permission to set " + reliefType + " relief type");
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
}
