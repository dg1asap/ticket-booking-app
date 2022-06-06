package touk.ticketbookingapp.entity;

import org.javamoney.moneta.Money;
import touk.ticketbookingapp.exception.customer.CustomerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Customer {
    protected final String name;
    protected final String surname;
    protected String reliefType;
    private final Map <Integer, Money> movieShowFees;

    public Customer(String name, String surname) throws CustomerException {
        validate(name, surname);

        this.name = name;
        this.surname = surname;
        this.reliefType = "adult";
        movieShowFees = new HashMap<>();
    }

    public void setRelief(String reliefType) {
        this.reliefType = reliefType;
    }

    public void addFeeForMovieShow(int movieShowId, Money fee) {
        if (movieShowFees.containsKey(movieShowId)) {
            Money moneyForMovieShow = movieShowFees.get(movieShowId);
            moneyForMovieShow.add(fee);
        } else {
            movieShowFees.put(movieShowId, fee);
        }
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public boolean hasSurname(String surname) {
        return this.surname.equals(surname);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getReliefType() {
        return reliefType;
    }

    public Set<Integer> getIdOfMovieShowsToPaid() {
        return movieShowFees.keySet();
    }

    public Money getSumOfFees() {
        Money sum = Money.of(0, "PLN");
        for (Money monetaryAmount : movieShowFees.values())
            sum = sum.add(monetaryAmount);
        return sum;
    }

    private void validate(String name, String surname) throws CustomerException {
        try {
            CustomerValidator.validate(name, surname);
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



}
