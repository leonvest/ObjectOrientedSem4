package se.kth.iv1350.bikerepair.model;

/**
 * Strategy for applying a discount to a repair order's base cost.
 * Concrete implementations include {@link NoDiscount},
 * {@link LoyaltyDiscount}, and {@link WinterDiscount}.
 */
public interface DiscountStrategy {

    /**
     * Returns the cost after applying this discount.
     *
     * @param baseCost the cost before discount
     * @return the cost after discount; never <code>null</code>
     */
    Amount applyTo(Amount baseCost);
}
