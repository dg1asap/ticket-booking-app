package touk.ticketbookingapp.entity;

import touk.ticketbookingapp.exception.customer.CapitalLetterException;
import touk.ticketbookingapp.exception.customer.DashException;
import touk.ticketbookingapp.exception.customer.LengthException;
import touk.ticketbookingapp.exception.customer.SpecialCharacterException;

public class CustomerValidator {
    public static void validate(String name, String surname)
            throws CapitalLetterException, LengthException, DashException, SpecialCharacterException {
        validateName(name);
        validateSurname(surname);
    }

    public static void validate(Customer customer)
            throws CapitalLetterException, LengthException, DashException, SpecialCharacterException {
        String name = customer.getName();
        String surname = customer.getSurname();
        validateName(name);
        validateSurname(surname);
    }

    private static void validateName(String name) throws CapitalLetterException, LengthException {
        validateLengthOfName(name);
        validateFirstLetterOfName(name);
    }
    private static void validateSurname(String surname)
            throws CapitalLetterException, DashException, LengthException, SpecialCharacterException {

        validateLengthOfSurname(surname);
        validateFirstLetterOfSurname(surname);
        validateDashInSurname(surname);
        validateCapitalLetterInSurname(surname);
        validateSpecialCharacterInSurname(surname);
    }

    private static void validateLengthOfName(String name) throws LengthException {
        if (!hasProperLength(name))
            throw new LengthException("Name: " + name + " of customer is too short, should be more than 2 letters");
    }

    private static void validateFirstLetterOfName(String name) throws CapitalLetterException {
        if (!startWithCapitalLetter(name))
            throw new CapitalLetterException("First char of name: " + name + " must be capital letter");
    }

    private static void validateLengthOfSurname(String surname) throws LengthException {
        if (!hasProperLength(surname))
            throw new LengthException(
                    "Surname: " + surname + " of customer is too short, should be more than 2 letters");
    }

    private static void validateCapitalLetterInSurname(String surname) throws CapitalLetterException {
        validateFirstLetterOfSurname(surname);
        validateLetterAfetDashInSurname(surname);
    }

    private static void validateDashInSurname(String surname) throws DashException {
        if (!isMaxOneDash(surname))
            throw new DashException(
                    "In surname: " + surname + "can be max one dash");
    }

    private static void validateSpecialCharacterInSurname(String surname) throws SpecialCharacterException {
        if (isNotAllowedSpecialCharacter(surname))
            throw new SpecialCharacterException(
                    "If you want to add the second part of surname: " + surname + " use a dash");
    }

    private static void validateFirstLetterOfSurname(String surname) throws CapitalLetterException {
        if (!startWithCapitalLetter(surname))
            throw new CapitalLetterException(
                    "First char of surname: " + surname + " must be capital letter");
    }

    private static void validateLetterAfetDashInSurname(String surname) throws CapitalLetterException {
        if (!isCapitalLetterAfterDash(surname))
            throw new CapitalLetterException(
                    "In surname: " + surname + " next character after dash start with capital letter");
    }

    private static boolean hasProperLength(String word) {
        return word.length() >= 3;
    }

    private static boolean isMaxOneDash(String word) {
        long count = word.chars().filter(ch -> ch == '-').count();
        return count <= 1;
    }

    private static boolean isNotAllowedSpecialCharacter(String word) {
        long count = word.chars().filter(ch -> ch == ' ' || ch == '_').count();
        return count > 0;
    }

    private static boolean startWithCapitalLetter(String word) {
        char fistChar = word.charAt(0);
        return Character.isUpperCase(fistChar);
    }

    private static boolean isCapitalLetterAfterDash(String word) {
        int index = word.indexOf('-');
        if (word.length() > index + 1) {
            char letterAfterDash = word.charAt(index + 1);
            return Character.isUpperCase(letterAfterDash);
        }
        return false;
    }

}
