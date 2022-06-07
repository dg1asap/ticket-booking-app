package touk.ticketbookingapp.entity;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest {
    public static Checkout checkout;

    @BeforeAll
    static void createTicketOffice() {
        HashMap<String, Money> ticketTypeFees = new HashMap<>();
        ticketTypeFees.put("adult", Money.of(25.00, "PLN"));
        ticketTypeFees.put("student", Money.of(18.00, "PLN"));
        ticketTypeFees.put("child", Money.of(12.50, "PLN"));
        checkout = new Checkout(ticketTypeFees);
    }
    @Test
    void getTicketAmountInPLNTest() {
        assertEquals("PLN 25", checkout.getTicketAmountInPLN("adult").toString());
        assertEquals("PLN 18", checkout.getTicketAmountInPLN("student").toString());
        assertEquals("PLN 12.5", checkout.getTicketAmountInPLN("child").toString());
    }

}
