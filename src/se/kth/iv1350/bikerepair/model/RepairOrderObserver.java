package se.kth.iv1350.bikerepair.model;

import se.kth.iv1350.bikerepair.integration.RepairOrderDTO;

/**
 * Observer interface for parties that wish to be notified whenever a
 * {@link RepairOrder} is updated. Implementations must not call the
 * controller or any other application code; they may only react to the
 * snapshot they receive.
 */
public interface RepairOrderObserver {

    /**
     * Called by a {@link RepairOrder} after it has been updated.
     *
     * @param order an immutable snapshot of the order in its new state
     */
    void updated(RepairOrderDTO order);
}
