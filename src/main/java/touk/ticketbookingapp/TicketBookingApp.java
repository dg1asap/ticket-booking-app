package touk.ticketbookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TicketBookingApp {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingApp.class, args);
	}

}