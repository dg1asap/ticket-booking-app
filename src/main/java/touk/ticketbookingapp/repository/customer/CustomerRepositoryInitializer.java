package touk.ticketbookingapp.repository.customer;

import touk.ticketbookingapp.entity.Customer;

public class CustomerRepositoryInitializer {
    public static void init(CustomerRepository repository) {
        Customer customer1 = new Customer("Will", "Smith");
        Customer customer2 = new Customer("Johnny", "Depp");
        Customer customer3 = new Customer("Eva", "Polka");

        repository.addCustomer(customer1);
        repository.addCustomer(customer2);
        repository.addCustomer(customer3);
    }
}
