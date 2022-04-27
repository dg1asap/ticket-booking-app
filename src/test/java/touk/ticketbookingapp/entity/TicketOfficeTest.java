package touk.ticketbookingapp.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static touk.ticketbookingapp.entity.TicketOffice.getTicketAmountInPLN;

public class TicketOfficeTest {
    @Test
    void getTicketAmountInPLNTest() {
        assertEquals(25, getTicketAmountInPLN("adult"));
        assertEquals(18, getTicketAmountInPLN("student"));
        assertEquals(12.5, getTicketAmountInPLN("child"));
    }

}
