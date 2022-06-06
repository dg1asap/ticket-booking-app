package touk.ticketbookingapp.repository.customer;

import org.springframework.stereotype.Repository;
import touk.ticketbookingapp.entity.Customer;
import touk.ticketbookingapp.exception.customer.CustomerException;
import touk.ticketbookingapp.util.Collector;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class LocalCustomerRepository implements CustomerRepository {
    private final List<Customer> customers;

    public LocalCustomerRepository() {
        customers = new ArrayList<>();
        CustomerRepositoryInitializer.init(this);
    }

    @Override
    public void addCustomer(Customer customer) throws CustomerException {
        if (hasCustomer(customer)) {
            String name = customer.getName();
            String surname = customer.getSurname();
            throw new CustomerException("Customer with name: " + name + " and surname " + surname + " is already in repository");
        }
        customers.add(customer);
    }

    @Override
    public boolean hasCustomer(Customer customer) {
        String name = customer.getName();
        String surname = customer.getSurname();
        return customers.stream()
                .anyMatch(customer1 -> customer1.hasName(name) && customer1.hasSurname(surname));
    }

    @Override
    public Customer getCustomerWithNameAndSurname(String name, String surname) {
        try {
            return customers.stream()
                    .filter(customer -> customer.hasName(name) && customer.hasSurname(surname))
                    .collect(Collector.toSingleton());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            throw new NoSuchElementException("Customer with name " + name + " and surname " + surname + " not found or found more than one");
        }
    }


}
