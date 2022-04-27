package touk.ticketbookingapp.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Customer {
    protected final String name;
    protected final String surname;
    protected String reliefType;
    private Map <Integer, Double> movieShowFees;

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.reliefType = "adult";
        movieShowFees = new HashMap<>();

        validateName();
        validateSurname();
    }

    public void setRelief(String reliefType) {
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

    private void validateName() {
        if(hasProperLength(name)) {
            if (!startWithCapitalLetter(name))
                throw new IllegalArgumentException("First char of name: " + name + " must be capital letter");
        } else {
            throw new IllegalArgumentException("Name: " + name + " of customer is to short");
        }
    }

    private void validateSurname() {
        if (hasProperLength(surname)) {
            if (!startWithCapitalLetter(surname))
                throw new IllegalArgumentException(
                        "First char of surname: " + surname + " must be capital letter");
            if (!isMaxOneDash(surname))
                throw new IllegalArgumentException(
                        "In surname: " + surname + "can be max one dash");
            if (!isCapitalLetterAfterDash(surname))
                throw new IllegalArgumentException(
                        "In surname: " + surname + " next character after dash start with capital letter");
            if (isNotAllowedSpecialCharacter(surname))
                throw new IllegalArgumentException(
                        "If you want to add the second part of surname: " + surname + " use a dash");
        } else {
            throw new IllegalArgumentException(
                    "Surname: " + surname + " of customer is too short");
        }
    }

    private boolean hasProperLength(String word) {
        return word.length() >= 3;
    }

    private boolean isMaxOneDash(String word) {
        long count = word.chars().filter(ch -> ch == '-').count();
        return count <= 1;
    }

    private boolean isCapitalLetterAfterDash(String word) {
        int index = word.indexOf('-');
        if (word.length() > index + 1) {
            char letterAfterDash = word.charAt(index + 1);
            return Character.isUpperCase(letterAfterDash);
        }
        return false;
    }

    private boolean isNotAllowedSpecialCharacter(String word) {
        long count = word.chars().filter(ch -> ch == ' ' || ch == '_').count();
        return count > 0;
    }

    private boolean startWithCapitalLetter(String word) {
        char fistChar = word.charAt(0);
        return Character.isUpperCase(fistChar);
    }

}
