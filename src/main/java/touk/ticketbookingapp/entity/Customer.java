package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.exception.customer.CustomerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Customer {
    protected final String name;
    protected final String surname;
    protected String reliefType;
    private final Map <Integer, Double> movieShowFees;

    public Customer(String name, String surname) throws CustomerException {
        validate(name, surname);

        this.name = name;
        this.surname = surname;
        this.reliefType = "adult";
        movieShowFees = new HashMap<>();
    }

    public void validate(String name, String surname) throws CustomerException {
        try {
            CustomerValidator.validate(name, surname);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void setRelief(String reliefType) throws IllegalAccessException {
        if (!TicketOffice.isReliefType(reliefType))
            throw new IllegalAccessException("Can't set relief type to customer " + this.name + " " + this.surname);
        this.reliefType = reliefType;
    }

    public void addFeeForMovieShow(int movieShowId) {
        double fee = TicketOffice.getTicketAmountInPLN(reliefType);
        if (movieShowFees.containsKey(movieShowId))
            movieShowFees.put(movieShowId, movieShowFees.get(movieShowId) + fee);
        else
            movieShowFees.put(movieShowId, fee);
    }

    public double getSumOfFees() {
        return movieShowFees.values().stream()
                .mapToDouble(Double::doubleValue).sum();
    }

    public Set<Integer> getIdOfMovieShowsToPaid() {
        return movieShowFees.keySet();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public boolean hasSurname(String surname) {
        return this.surname.equals(surname);
    }

}
