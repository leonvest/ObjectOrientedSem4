package se.kth.iv1350.bikerepair.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link NoDiscount}, the identity discount strategy.
 */
public class NoDiscountTest {

    @Test
    public void testApplyToReturnsTheInputUnchanged() {
        Amount input = new Amount(1000);
        assertEquals(input, new NoDiscount().applyTo(input),
                "NoDiscount must return the input amount unchanged.");
    }

    @Test
    public void testApplyToReturnsZeroForZero() {
        assertEquals(new Amount(0), new NoDiscount().applyTo(new Amount(0)),
                "NoDiscount on zero must return zero.");
    }
}
