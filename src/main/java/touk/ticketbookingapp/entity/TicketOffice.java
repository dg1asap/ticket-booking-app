package touk.ticketbookingapp.entity;

import java.util.NoSuchElementException;

public class TicketOffice {
    public static double getTicketAmountInPLN(String reliefType) {
        return switch (reliefType) {
            case "adult" -> 25.0;
            case "student" -> 18.0;
            case "child" -> 12.5;
            default -> throw new NoSuchElementException("Relief type: " + reliefType + " don't exist");
        };
    }
}
