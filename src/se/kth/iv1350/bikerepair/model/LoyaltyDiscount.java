package se.kth.iv1350.bikerepair.model;

/**
 * A {@link DiscountStrategy} that gives loyal customers a 10% discount.
 */
public final class LoyaltyDiscount implements DiscountStrategy {
    private static final int PERCENT_OFF = 10;

    /**
     * Returns 90% of the input amount, rounded down to the nearest unit.
     *
     * @param baseCost the cost before discount
     * @return the discounted cost
     */
    @Override
    public Amount applyTo(Amount baseCost) {
        int discounted = baseCost.getValue() * (100 - PERCENT_OFF) / 100;
        return new Amount(discounted);
    }

    /**
     * @return a human-readable label for this discount
     */
    @Override
    public String toString() {
        return PERCENT_OFF + "% loyalty discount";
    }
}
