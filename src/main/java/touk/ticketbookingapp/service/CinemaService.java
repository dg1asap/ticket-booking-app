package touk.ticketbookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import touk.ticketbookingapp.entity.*;
import touk.ticketbookingapp.repository.CinemaRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CinemaService {

    @Autowired
    CinemaRepository cinemaRepository;

    public Set<MovieShow> getMovieShows() {
        return cinemaRepository.getMovieShows();
    }

    public List<MovieShow> getSortedMovieShowsInPeriod(LocalDateTime fromCalendar, LocalDateTime toCalendar) {
        return cinemaRepository.getSortedMovieShowsInPeriod(fromCalendar, toCalendar);
    }

    public Room getRoomByMovieShowId(int id) {
        return cinemaRepository.getRoomByMovieShowId(id);
    }

    public void bookingSeatOnMovieShowForCustomer(
            int seatId, int movieShowId, String nameOfCustomer, String surnameOfCustomer) throws IllegalAccessException {

        Customer customer = getCustomerWithNameAndSurname(nameOfCustomer, surnameOfCustomer);
        MovieShow movieShow = cinemaRepository.getMovieShowWithId(movieShowId);
        Room room = cinemaRepository.getRoomByMovieShowId(movieShowId);

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
        cinemaRepository.addCustomer(customer);
    }

    public List<Seat> getAvailableSeatsOnMovieShow(int movieShowId) {
        MovieShow movieShow = cinemaRepository.getMovieShowWithId(movieShowId);
        Room room = cinemaRepository.getRoomByMovieShowId(movieShowId);

        return room.getAvailableSeatsOnMovieShow(movieShow);
    }

    public void setCustomerReliefType(String name, String surname, String reliefType) {
        Customer customer = getCustomerWithNameAndSurname(name, surname);
        customer.setRelief(reliefType);
    }

    public Customer getCustomerWithNameAndSurname(String name, String surname) {
        return cinemaRepository.getCustomerWithNameAndSurname(name, surname);
    }

    public List<Reservation> getReservationOfCustomerOnMovieShow(String name, String surname, int movieShowId) {
        Customer customer = getCustomerWithNameAndSurname(name, surname);
        Room room = getRoomByMovieShowId(movieShowId);
        MovieShow movieShow = cinemaRepository.getMovieShowWithId(movieShowId);
        return room.getReservationsOfCustomerOnMovieShow(customer, movieShow);
    }

    public List<Reservation> getReservationOfCustomer(String name, String surname) {
        Customer customer = getCustomerWithNameAndSurname(name, surname);
        List<Room> rooms = cinemaRepository.getRooms();
        return rooms.stream()
                .map(room1 -> room1.getReservationsOfCustomer(customer))
                .flatMap(Collection::stream)
                .toList();
    }

}
