package se.kth.iv1350.bikerepair.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link LoyaltyDiscount}, which takes 10% off the base
 * cost.
 */
public class LoyaltyDiscountTest {

    @Test
    public void testApplyToTakesTenPercentOff() {
        assertEquals(new Amount(900),
                new LoyaltyDiscount().applyTo(new Amount(1000)),
                "LoyaltyDiscount on 1000 must return 900.");
    }

    @Test
    public void testApplyToDoesNotMutateInput() {
        Amount input = new Amount(1000);
        new LoyaltyDiscount().applyTo(input);
        assertEquals(new Amount(1000), input,
                "LoyaltyDiscount must not mutate its input.");
    }

    @Test
    public void testApplyToRoundsDownOnFractionalAmounts() {
        assertEquals(new Amount(89),
                new LoyaltyDiscount().applyTo(new Amount(99)),
                "10% off 99 must round down to 89.");
    }

    @Test
    public void testApplyToReturnsZeroForZero() {
        assertEquals(new Amount(0),
                new LoyaltyDiscount().applyTo(new Amount(0)),
                "LoyaltyDiscount on zero must return zero.");
    }
}
