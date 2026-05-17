package se.kth.iv1350.bikerepair.integration;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Stands in for the external customer database. The registry holds a small
 * hard-coded set of customers and looks them up by phone number.
 */
public class CustomerRegistry {
    private static final String DB_FAILURE_TRIGGER = "0700000000";

    private final List<CustomerDTO> customers = new ArrayList<>();

    /**
     * A registry created is pre-populated with sample customers.
     */
    public CustomerRegistry() {
        addSampleCustomers();
    }

    /**
     * Looks up a customer by phone number.
     *
     * @param phoneNumber the phone number to search for
     * @return the matching customer
     * @throws CustomerNotFoundException if no customer has the specified
     *                                   phone number
     * @throws DatabaseFailureException  if the simulated database call fails
     *                                   (triggered by a hardcoded magic
     *                                   phone number)
     */
    public CustomerDTO findCustomer(PhoneNumber phoneNumber)
            throws CustomerNotFoundException {
        if (phoneNumber.getNumber().equals(DB_FAILURE_TRIGGER)) {
            throw new DatabaseFailureException(phoneNumber);
        }
        for (CustomerDTO customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        throw new CustomerNotFoundException(phoneNumber);
    }

    private void addSampleCustomers() {
        customers.add(new CustomerDTO(
                "Anna Andersson",
                "anna@example.com",
                new PhoneNumber("0701234567"),
                new BikeDTO("VanMoof", "S5", "VM-S5-00123")));
        customers.add(new CustomerDTO(
                "Bertil Bengtsson",
                "bertil@example.com",
                new PhoneNumber("0709876543"),
                new BikeDTO("Cake", "Makka", "CK-MK-00987")));
        customers.add(new CustomerDTO(
                "Cecilia Carlsson",
                "cecilia@example.com",
                new PhoneNumber("0735551122"),
                new BikeDTO("Cowboy", "Cruiser", "CB-CR-00456")));
    }
}
