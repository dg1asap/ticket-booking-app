package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import touk.ticketbookingapp.exception.customer.CustomerException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private static Customer david;
    private static Customer will;
    private static Customer grzegorz;

    @BeforeAll
    public static void createCustomers() {
        try {
            david = new Customer("David", "Jones");
            will = new Customer("Will", "Smith");
            grzegorz = new Customer("Grzegorz", "Brzęczyszczykiewicz");
        } catch (CustomerException e) {
            e.getMessage();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Yu", "Lu", "Ka"})
    void tooShortNameTest(String name) {
        String surname = "Surname";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "Name: " + name + " of customer is too short, should be more than 2 letters");

    }

    @ParameterizedTest
    @ValueSource(strings = {"pieter", "tom", "albert"})
    void nameStartWithCapitalLetterTest(String name) {
        String surname = "Surname";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "First char of name: " + name + " must be capital letter");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Xi", "Le", "Tu"})
    void tooShortSurnameTest(String surname) {
        String name = "Name";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "Surname: " + surname + " of customer is too short, should be more than 2 letters");
    }

    @ParameterizedTest
    @ValueSource(strings = {"jackson", "kennedy", "jordan"})
    void surnameStartWithCapitalLetterTest(String surname) {
        String name = "Name";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "First char of surname: " + surname + " must be capital letter");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Aaa-Bbb-Ccc-Ddd-Eee", "Aaa-Bee-Cc"})
    void surnameHasMaxOneDashTest(String surname) {
        String name = "Name";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "In surname: " + surname + "can be max one dash");
    }

    @ParameterizedTest
    @ValueSource(strings = {"We-we", "Xia-lu"})
    void isCapitalLetterAfterDashInSurnameTest(String surname) {
        String name = "Name";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "In surname: " + surname + " next character after dash start with capital letter");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Mieszko I", "Jan III Sobieski"})
    void isNotAllowedSpecialCharacterInSurnameTest(String surname) {
        String name = "Name";
        assertIllegalArgumentExceptionMessageInCustomerConstructor(name, surname,
                "If you want to add the second part of surname: " + surname + " use a dash");
    }

    @Test
    void setReliefTest() {
        try {
            david.setRelief("student");
            grzegorz.setRelief("child");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(david.reliefType, "student");
        assertEquals(will.reliefType, "adult");
        assertEquals(grzegorz.reliefType, "child");
   }

   @Test
   void addFeeForMovieShowsAndGetSumOfFeesTest() {
        will.addFeeForMovieShow(222);
        will.addFeeForMovieShow(43112341);
        assertEquals(2*25, will.getSumOfFees());

        try {
           grzegorz.setRelief("child");
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        grzegorz.addFeeForMovieShow(222);
        grzegorz.addFeeForMovieShow(42);
        grzegorz.addFeeForMovieShow(4444);
        assertEquals(3*12.5, grzegorz.getSumOfFees());
   }

   @Test
   void getIdOfMovieShowsToPaidTest() {
       will.addFeeForMovieShow(223);
       will.addFeeForMovieShow(11);
       grzegorz.addFeeForMovieShow(1324);
       grzegorz.addFeeForMovieShow(99909);

       assertTrue(will.getIdOfMovieShowsToPaid().contains(223));
       assertTrue(will.getIdOfMovieShowsToPaid().contains(11));
       assertTrue(grzegorz.getIdOfMovieShowsToPaid().contains(1324));
       assertTrue(grzegorz.getIdOfMovieShowsToPaid().contains(99909));

       assertFalse(will.getIdOfMovieShowsToPaid().contains(23214));
       assertFalse(will.getIdOfMovieShowsToPaid().contains(111));
       assertFalse(grzegorz.getIdOfMovieShowsToPaid().contains(1224));
       assertFalse(grzegorz.getIdOfMovieShowsToPaid().contains(99999));
   }

   @Test
   void hasNameTest() {
        assertTrue(david.hasName("David"));
        assertTrue(will.hasName("Will"));
        assertTrue(grzegorz.hasName("Grzegorz"));

        assertFalse(david.hasName("Da2231vid"));
        assertFalse(will.hasName("Willi"));
        assertFalse(grzegorz.hasName("Zbigniew"));
   }

   @Test
   void hasSurnameTest() {
       assertTrue(david.hasSurname("Jones"));
       assertTrue(will.hasSurname("Smith"));
       assertTrue(grzegorz.hasSurname("Brzęczyszczykiewicz"));

       assertFalse(david.hasSurname("Smith"));
       assertFalse(will.hasSurname("Brzęczyszczykiewicz"));
       assertFalse(grzegorz.hasSurname("Jones"));
   }

   @Test
   void equalsTest() {
        try {
            Customer copyOfDavid = new Customer("David", "Jones");
            assertEquals(copyOfDavid.name, david.name);
            assertEquals(copyOfDavid.surname, david.surname);
        } catch (CustomerException e ) {
            e.getMessage();
        }

        assertNotEquals(david.name, will.name);
        assertNotEquals(david.name, grzegorz.name);
        assertNotEquals(will.surname, grzegorz.surname);
   }

   private void assertIllegalArgumentExceptionMessageInCustomerConstructor(String name, String surname, String message) {
        Throwable exception = assertThrows(CustomerException.class, () -> {
            Customer customer = new Customer(name, surname);
        });
        String exceptionMessage = exception.getMessage();
        assertEquals(message, exceptionMessage);
    }

}
