package se.kth.iv1350.bikerepair.integration;

import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Thrown when the customer database cannot be reached. This represents
 * an infrastructure failure that cannot be reasonably recovered from
 * inside the program; therefore, it is an unchecked exception.
 */
public class DatabaseFailureException extends RuntimeException {
    private final PhoneNumber searchedPhone;

    /**
     * Creates a new instance describing the failed database call.
     *
     * @param searchedPhone the phone number that was being looked up when
     *                      the database call failed
     */
    public DatabaseFailureException(PhoneNumber searchedPhone) {
        super("Database call failed while searching for phone number "
                + searchedPhone);
        this.searchedPhone = searchedPhone;
    }

    /**
     * Creates a new instance with an underlying cause, allowing the
     * original infrastructure exception to be chained.
     *
     * @param searchedPhone the phone number that was being looked up
     * @param cause the underlying exception that triggered this failure
     */
    public DatabaseFailureException(PhoneNumber searchedPhone, Throwable cause) {
        super("Database call failed while searching for phone number "
                + searchedPhone, cause);
        this.searchedPhone = searchedPhone;
    }

    /**
     * @return the phone number that was being looked up when the database
     *         call failed
     */
    public PhoneNumber getSearchedPhone() {
        return searchedPhone;
    }
}
