package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Unit tests for {@link DatabaseFailureException}. Verifies both
 * constructors, the carried phone number, and cause chaining.
 */
public class DatabaseFailureExceptionTest {
    private static final PhoneNumber SEARCHED = new PhoneNumber("0700000000");

    @Test
    public void testConstructorStoresSearchedPhone() {
        DatabaseFailureException exception = new DatabaseFailureException(SEARCHED);
        assertEquals(SEARCHED, exception.getSearchedPhone(),
                "getSearchedPhone must return the phone passed to the constructor.");
    }

    @Test
    public void testGetMessageContainsTheSearchedPhone() {
        DatabaseFailureException exception = new DatabaseFailureException(SEARCHED);
        String message = exception.getMessage();
        assertNotNull(message, "The exception must have a non-null message.");
        assertTrue(message.contains(SEARCHED.toString()),
                "The message must mention the searched phone number.");
    }

    @Test
    public void testCauseConstructorChainsTheUnderlyingException() {
        Throwable cause = new RuntimeException("Connection refused");
        DatabaseFailureException exception =
                new DatabaseFailureException(SEARCHED, cause);
        assertSame(cause, exception.getCause(),
                "The cause-aware constructor must preserve the underlying exception.");
    }
}
