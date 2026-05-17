package se.kth.iv1350.bikerepair.integration;

import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Thrown when a search is made for a phone number that is not registered
 * in the customer registry. This is a recoverable business condition,
 * so the exception is checked.
 */
public class CustomerNotFoundException extends Exception {
    private final PhoneNumber searchedPhone;

    /**
     * Creates a new instance describing the failed lookup.
     *
     * @param searchedPhone the phone number that could not be found
     */
    public CustomerNotFoundException(PhoneNumber searchedPhone) {
        super("No customer found for phone number " + searchedPhone);
        this.searchedPhone = searchedPhone;
    }

    /**
     * @return the phone number that was searched for when the
     *         exception was thrown
     */
    public PhoneNumber getSearchedPhone() {
        return searchedPhone;
    }
}
