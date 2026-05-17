package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Unit tests for {@link CustomerNotFoundException}. Verifies that the
 * exception carries the searched phone number and provides a useful
 * <code>getMessage()</code>.
 */
public class CustomerNotFoundExceptionTest {
    private static final PhoneNumber SEARCHED = new PhoneNumber("0000999111");

    @Test
    public void testConstructorStoresSearchedPhone() {
        CustomerNotFoundException exception = new CustomerNotFoundException(SEARCHED);
        assertEquals(SEARCHED, exception.getSearchedPhone(),
                "getSearchedPhone must return the phone passed to the constructor.");
    }

    @Test
    public void testGetMessageContainsTheSearchedPhone() {
        CustomerNotFoundException exception = new CustomerNotFoundException(SEARCHED);
        String message = exception.getMessage();
        assertNotNull(message, "The exception must have a non-null message.");
        assertTrue(message.contains(SEARCHED.toString()),
                "The message must mention the searched phone number.");
    }
}
