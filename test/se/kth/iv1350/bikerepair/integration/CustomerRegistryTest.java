package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Unit tests for {@link CustomerRegistry}. Covers the happy lookup path
 * plus both error conditions: customer not found, and the simulated
 * database failure triggered by a hardcoded magic phone number.
 */
public class CustomerRegistryTest {
    private static final PhoneNumber ANNA_PHONE = new PhoneNumber("0701234567");
    private static final PhoneNumber BERTIL_PHONE = new PhoneNumber("0709876543");
    private static final PhoneNumber UNKNOWN_PHONE = new PhoneNumber("0000999111");
    private static final PhoneNumber DB_FAILURE_PHONE = new PhoneNumber("0700000000");

    private CustomerRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new CustomerRegistry();
    }

    @AfterEach
    public void tearDown() {
        registry = null;
    }

    @Test
    public void testFindCustomerReturnsMatchingCustomer() throws CustomerNotFoundException {
        CustomerDTO found = registry.findCustomer(ANNA_PHONE);
        assertNotNull(found,
                "findCustomer must return a customer when one matches the number.");
    }

    @Test
    public void testFindCustomerReturnsCustomerWithMatchingPhone() throws CustomerNotFoundException {
        CustomerDTO found = registry.findCustomer(ANNA_PHONE);
        assertEquals(ANNA_PHONE, found.getPhoneNumber(),
                "The returned customer's phone number must match the searched number.");
    }

    @Test
    public void testFindCustomerThrowsCustomerNotFoundForUnknownPhone() {
        assertThrows(CustomerNotFoundException.class,
                () -> registry.findCustomer(UNKNOWN_PHONE),
                "findCustomer must throw CustomerNotFoundException for an unknown phone.");
    }

    @Test
    public void testCustomerNotFoundExceptionCarriesSearchedPhone() {
        CustomerNotFoundException thrown = assertThrows(
                CustomerNotFoundException.class,
                () -> registry.findCustomer(UNKNOWN_PHONE),
                "The exception must be thrown for an unknown phone.");
        assertEquals(UNKNOWN_PHONE, thrown.getSearchedPhone(),
                "The exception must carry the phone number that was searched for.");
    }

    @Test
    public void testFindCustomerThrowsDatabaseFailureForMagicPhone() {
        assertThrows(DatabaseFailureException.class,
                () -> registry.findCustomer(DB_FAILURE_PHONE),
                "findCustomer must throw DatabaseFailureException for the magic phone.");
    }

    @Test
    public void testDatabaseFailureExceptionCarriesSearchedPhone() {
        DatabaseFailureException thrown = assertThrows(
                DatabaseFailureException.class,
                () -> registry.findCustomer(DB_FAILURE_PHONE),
                "The exception must be thrown for the magic phone.");
        assertEquals(DB_FAILURE_PHONE, thrown.getSearchedPhone(),
                "The exception must carry the phone number that was searched for.");
    }

    @Test
    public void testRegistryStateIsUnchangedAfterUnknownLookup() throws CustomerNotFoundException {
        assertThrows(CustomerNotFoundException.class,
                () -> registry.findCustomer(UNKNOWN_PHONE));
        CustomerDTO foundAfter = registry.findCustomer(ANNA_PHONE);
        assertEquals(ANNA_PHONE, foundAfter.getPhoneNumber(),
                "An unsuccessful lookup must leave the registry able to find others.");
    }

    @Test
    public void testFindCustomerCanLookUpDifferentCustomers() throws CustomerNotFoundException {
        CustomerDTO anna = registry.findCustomer(ANNA_PHONE);
        CustomerDTO bertil = registry.findCustomer(BERTIL_PHONE);
        assertNotEquals(anna.getName(), bertil.getName(),
                "Different phone numbers must resolve to different customers.");
    }
}
