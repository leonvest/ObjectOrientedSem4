package se.kth.iv1350.bikerepair.model;

/**
 * A {@link DiscountStrategy} that applies no discount. Used as the
 * default for new repair orders.
 */
public final class NoDiscount implements DiscountStrategy {

    /**
     * Returns the input amount unchanged.
     *
     * @param baseCost the cost before discount
     * @return the same amount
     */
    @Override
    public Amount applyTo(Amount baseCost) {
        return baseCost;
    }

    /**
     * @return a human-readable label for this discount
     */
    @Override
    public String toString() {
        return "no discount";
    }
}
