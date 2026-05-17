package se.kth.iv1350.bikerepair.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link WinterDiscount}, which takes 15% off the base
 * cost.
 */
public class WinterDiscountTest {

    @Test
    public void testApplyToTakesFifteenPercentOff() {
        assertEquals(new Amount(850),
                new WinterDiscount().applyTo(new Amount(1000)),
                "WinterDiscount on 1000 must return 850.");
    }

    @Test
    public void testApplyToRoundsDownOnFractionalAmounts() {
        assertEquals(new Amount(84),
                new WinterDiscount().applyTo(new Amount(99)),
                "15% off 99 must round down to 84.");
    }

    @Test
    public void testApplyToReturnsZeroForZero() {
        assertEquals(new Amount(0),
                new WinterDiscount().applyTo(new Amount(0)),
                "WinterDiscount on zero must return zero.");
    }
}
