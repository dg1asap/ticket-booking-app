package touk.ticketbookingapp.repository.customer;

import touk.ticketbookingapp.entity.Customer;
import touk.ticketbookingapp.exception.customer.CustomerException;

public interface CustomerRepository {
    void addCustomer(Customer customer) throws CustomerException;
    boolean hasCustomer(Customer customer);
    Customer getCustomerWithNameAndSurname(String name, String surname);

}
