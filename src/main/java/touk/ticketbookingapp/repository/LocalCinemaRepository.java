package touk.ticketbookingapp.repository;

import org.springframework.stereotype.Repository;
import touk.ticketbookingapp.entity.*;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalDateTime.of;

@Repository
public class LocalCinemaRepository implements CinemaRepository {
    private Map<MovieShow, Room> movieShowRoomMap;
    private List<Customer> customers;
    private int seatIdCounter = 9800001;

    public LocalCinemaRepository() {
        movieShowRoomMap = new HashMap<>();
        customers = new ArrayList<>();

        LocalDateTime start1 = LocalDateTime.of(2022, 1, 1, 6, 0);
        LocalDateTime start2 = LocalDateTime.of(2022, 1, 1, 12, 0);
        LocalDateTime start3 = LocalDateTime.of(2022, 1, 1, 16, 15);
        LocalDateTime start4 = LocalDateTime.of(2022, 1, 1, 21, 30);

        LocalDateTime start5 = LocalDateTime.of(2022, 5, 27, 10, 0);
        LocalDateTime start6 = LocalDateTime.of(2022, 5, 27, 15, 20);
        LocalDateTime start7 = LocalDateTime.of(2022, 5, 27, 20, 0);

        LocalDateTime start8 = LocalDateTime.of(2022, 10, 28, 9, 0);
        LocalDateTime start9 = LocalDateTime.of(2022, 10, 28, 17, 10);
        LocalDateTime start10 = LocalDateTime.of(2022, 10, 28, 20, 0);


        LocalDateTime end1 = LocalDateTime.of(2022, 1, 1, 10, 0);
        LocalDateTime end2 = LocalDateTime.of(2022, 1, 1, 14, 20);
        LocalDateTime end3 = LocalDateTime.of(2022, 1, 1, 19, 20);
        LocalDateTime end4 = LocalDateTime.of(2022, 1, 1, 23, 30);

        LocalDateTime end5 = LocalDateTime.of(2022, 5, 27, 12, 0);
        LocalDateTime end6 = LocalDateTime.of(2022, 5, 27, 18, 15);
        LocalDateTime end7 = LocalDateTime.of(2022, 5, 27, 22, 0);

        LocalDateTime end8 = LocalDateTime.of(2022, 10, 28, 11, 30);
        LocalDateTime end9 = LocalDateTime.of(2022, 10, 28, 18, 0);
        LocalDateTime end10 = LocalDateTime.of(2022, 10, 28, 21, 0);

        Movie fightClub = new Movie("Fight Club");
        Movie granTorino = new Movie("Gran Torino");
        Movie batman = new Movie("Batman");
        Movie seven = new Movie("Seven");
        Movie trumanShow = new Movie("Truman Show");

        MovieShow movieShow1 = new MovieShow(1001, fightClub, start1, end1);
        MovieShow movieShow2 = new MovieShow(1002, trumanShow, start2, end2);
        MovieShow movieShow3 = new MovieShow(1003, batman, start3, end3);
        MovieShow movieShow4 = new MovieShow(1004, fightClub, start4, end4);

        MovieShow movieShow5 = new MovieShow(1005, seven, start5, end5);

        MovieShow movieShow6 = new MovieShow(1006, trumanShow, start6, end6);
        MovieShow movieShow7 = new MovieShow(1007, batman, start7, end7);

        MovieShow movieShow8 = new MovieShow(1008, granTorino, start8, end8);
        MovieShow movieShow9 = new MovieShow(1009, seven, start9, end9);
        MovieShow movieShow10 = new MovieShow(1010, granTorino, start10, end10);

        Room room1 = new Room(1);
        Room room2 = new Room(2);
        Room room3 = new Room(3);
        Room room4 = new Room(4);

        addNSeatToRoom(50, room1);
        addNSeatToRoom(100, room2);
        addNSeatToRoom(200, room3);
        addNSeatToRoom(200, room4);

        movieShowRoomMap.put(movieShow1, room1);
        movieShowRoomMap.put(movieShow2, room1);
        movieShowRoomMap.put(movieShow3, room1);

        movieShowRoomMap.put(movieShow4, room2);
        movieShowRoomMap.put(movieShow5, room2);
        movieShowRoomMap.put(movieShow6, room2);

        movieShowRoomMap.put(movieShow7, room3);
        movieShowRoomMap.put(movieShow8, room3);

        movieShowRoomMap.put(movieShow9, room4);
        movieShowRoomMap.put(movieShow10, room4);


        Customer customer1 = new Customer("Will", "Smith");
        Customer customer2 = new Customer("Johnny", "Depp");
        Customer customer3 = new Customer("Eva", "Polka");

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
    }

    @Override
    public Set<MovieShow> getMovieShows() {
        return movieShowRoomMap.keySet();
    }

    @Override
    public List<Room> getRooms() {
        return movieShowRoomMap.values().stream().toList();
    }

    @Override
    public List<MovieShow> getSortedMovieShowsInPeriod(LocalDateTime from, LocalDateTime to) {
        List<MovieShow> movieShows = getMovieShowsInPeriod(from, to);
        return sortMovieShowsByTitleAndStart(movieShows);
    }

    @Override
    public Room getRoomByMovieShowId(int id) {
        try {
            MovieShow movieShow = getMovieShowWithId(id);
            return movieShowRoomMap.get(movieShow);
        }catch(NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Room getRoomWithId(int roomNumber) {
        for(Room room : movieShowRoomMap.values()) {
            if (room.hasNumber(roomNumber)) {
                return room;
            }
        }
        throw new NoSuchElementException("No room with number: " + roomNumber);
    }

    @Override
    public void addCustomer(Customer customer) {
        if (hasCustomer(customer))
            throw new NoSuchElementException("Customer with name: " + customer.getName() + " and surname " + customer.getSurname() + " is already in repository");
        customers.add(customer);
    }

    @Override
    public Customer getCustomerWithNameAndSurname(String name, String surname) {
        for (Customer customer : customers)
            if (customer.hasName(name) && customer.hasSurname(surname))
                return customer;
        throw new NoSuchElementException("No customer with name: " + name + " and surname: " + surname);
    }

    @Override
    public MovieShow getMovieShowWithId(int id) {
        for (MovieShow movieShow : movieShowRoomMap.keySet())
            if (movieShow.hasId(id))
                return movieShow;
        throw new NoSuchElementException("No screening with id: " + id);
    }

    private List<MovieShow> getMovieShowsInPeriod(LocalDateTime from, LocalDateTime to) {
        return movieShowRoomMap.keySet().stream()
                .filter(movieShow -> movieShow.isOverlappingWith(from, to))
                .toList();
    }

    private List<MovieShow> sortMovieShowsByTitleAndStart(List<MovieShow> unmodifiableMovieShows) {
        List<MovieShow> modifiableMovieShows = new ArrayList<>(unmodifiableMovieShows);
        modifiableMovieShows.sort(new MovieShowSortingComparator());
        return modifiableMovieShows;
    }

    public boolean hasCustomer(Customer customer) {
        String name = customer.getName();
        String surname = customer.getSurname();
        return customers.stream()
                .anyMatch(customer1 -> customer1.hasName(name) && customer1.hasSurname(surname));
    }

    private void addNSeatToRoom(int numberOfSeat, Room room) {
        int row = 1;
        int column = 1;
        for (int i = 0; i < numberOfSeat; i++) {
            if (row == 10) {
                row = 1;
                column++;
            }
            Seat seat = new Seat(seatIdCounter, row, column);
            row++;
            seatIdCounter++;
            room.addSeat(seat);
        }
    }

}
