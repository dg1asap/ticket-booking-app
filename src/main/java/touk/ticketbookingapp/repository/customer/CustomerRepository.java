package touk.ticketbookingapp.repository.customer;

import touk.ticketbookingapp.entity.Customer;

public interface CustomerRepository {
    void addCustomer(Customer customer);
    boolean hasCustomer(Customer customer);
    Customer getCustomerWithNameAndSurname(String name, String surname);

}
