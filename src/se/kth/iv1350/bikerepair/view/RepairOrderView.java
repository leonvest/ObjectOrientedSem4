package se.kth.iv1350.bikerepair.view;

import se.kth.iv1350.bikerepair.integration.RepairOrderDTO;
import se.kth.iv1350.bikerepair.model.RepairOrderObserver;

/**
 * A live view that prints repair-order updates to <code>System.out</code>
 * whenever a {@link se.kth.iv1350.bikerepair.model.RepairOrder} is
 * modified. Implements the Observer pattern; never calls the controller
 * or any other application code.
 */
public class RepairOrderView implements RepairOrderObserver {

    /**
     * Receives a fresh snapshot of an updated repair order and prints it
     * to <code>System.out</code> under a header that distinguishes it
     * from the view's regular output.
     *
     * @param order the updated order
     */
    @Override
    public void updated(RepairOrderDTO order) {
        System.out.println();
        System.out.println("[LIVE UPDATE]");
        System.out.println(order);
    }
}
