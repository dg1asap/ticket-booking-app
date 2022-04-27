package touk.ticketbookingapp.repository;

import org.springframework.stereotype.Repository;
import touk.ticketbookingapp.entity.*;

import java.util.*;

@Repository
public class LocalCinemaRepository implements CinemaRepository {
    private Map<MovieShow, Room> movieShowRoomMap;
    private List<Customer> customers;
    private int seatIdCounter = 9800001;

    public LocalCinemaRepository() {
        movieShowRoomMap = new HashMap<>();
        customers = new ArrayList<>();

        Calendar start1 = new GregorianCalendar();
        Calendar start2 = new GregorianCalendar();
        Calendar start3 = new GregorianCalendar();
        Calendar start4 = new GregorianCalendar();
        Calendar start5 = new GregorianCalendar();
        Calendar start6 = new GregorianCalendar();
        Calendar start7 = new GregorianCalendar();
        Calendar start8 = new GregorianCalendar();
        Calendar start9 = new GregorianCalendar();
        Calendar start10 = new GregorianCalendar();

        Calendar end1 = new GregorianCalendar();
        Calendar end2 = new GregorianCalendar();
        Calendar end3 = new GregorianCalendar();
        Calendar end4 = new GregorianCalendar();
        Calendar end5 = new GregorianCalendar();
        Calendar end6 = new GregorianCalendar();
        Calendar end7 = new GregorianCalendar();
        Calendar end8 = new GregorianCalendar();
        Calendar end9 = new GregorianCalendar();
        Calendar end10 = new GregorianCalendar();

        start1.set(2022, 1, 1, 6, 0);
        start2.set(2022, 1, 1, 12, 0);
        start3.set(2022, 1, 1, 16, 15);
        start4.set(2022, 1, 1, 21, 30);

        start5.set(2022, 5, 27, 10, 0);
        start6.set(2022, 5, 27, 15, 20);
        start7.set(2022, 5, 27, 20, 0);

        start8.set(2022, 10, 28, 9, 0);
        start9.set(2022, 10, 28, 17, 10);
        start10.set(2022, 10, 28, 20, 0);

        end1.set(2022, 1, 1, 10, 0);
        end2.set(2022, 1, 1, 14, 20);
        end3.set(2022, 1, 1, 19, 20);
        end4.set(2022, 1, 1, 23, 30);

        end5.set(2022, 5, 27, 12, 0);
        end6.set(2022, 5, 27, 18, 15);
        end7.set(2022, 5, 27, 22, 0);

        end8.set(2022, 10, 28, 11, 30);
        end9.set(2022, 10, 28, 18, 0);
        end10.set(2022, 10, 28, 21, 0);

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
    public List<MovieShow> getSortedMovieShowsInPeriod(Calendar from, Calendar to) {
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

    private List<MovieShow> getMovieShowsInPeriod(Calendar from, Calendar to) {
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
