package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {
    private static Movie granTorino;
    private static Movie grandFather;
    private static Movie wolfOfWallStreet;

    @BeforeAll
    public static void createMovies() {
        granTorino = new Movie("Gran Torino");
        grandFather = new Movie("Grandfather");
        wolfOfWallStreet = new Movie("The Wolf of Wall Street");
    }

    @Test
    public void getTittleTest() {
        assertEquals("Gran Torino",granTorino.getTittle());
        assertEquals("Grandfather", grandFather.getTittle());
        assertEquals("The Wolf of Wall Street", wolfOfWallStreet.getTittle());
    }
}
