package touk.ticketbookingapp.entity;

import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.Map;

public class Checkout {
    private final Map<String, Money> ticketTypeAmount;

    public Checkout(HashMap<String, Money> ticketTypeAmount) {
        this.ticketTypeAmount = ticketTypeAmount;
    }

    public boolean hasReliefType(String reliefType) {
        return ticketTypeAmount.keySet().stream()
                .anyMatch(ticketType -> ticketType.equals(reliefType));
    }

    public Money getTicketAmountInPLN(String reliefType) {
        Money amount = Money.of(0, "PLN");
        Money referenceAmount = ticketTypeAmount.get(reliefType);
        return amount.add(referenceAmount);
    }

}
