package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.model.DiagnosticResult;
import se.kth.iv1350.bikerepair.model.PhoneNumber;
import se.kth.iv1350.bikerepair.model.ProblemDescription;
import se.kth.iv1350.bikerepair.model.RepairOrder;
import se.kth.iv1350.bikerepair.model.RepairOrderObserver;

/**
 * Unit tests verifying that an observer registered with the
 * {@link RepairOrderRegistry} is automatically attached to every newly
 * created repair order.
 */
public class RepairOrderRegistryObserverTest {

    private static class CountingObserver implements RepairOrderObserver {
        int updates = 0;

        @Override
        public void updated(RepairOrderDTO order) {
            updates++;
        }
    }

    private RepairOrderRegistry registry;
    private CustomerDTO anna;

    @BeforeEach
    public void setUp() {
        registry = new RepairOrderRegistry();
        anna = new CustomerDTO(
                "Anna Andersson",
                "anna@example.com",
                new PhoneNumber("0701234567"),
                new BikeDTO("VanMoof", "S5", "VM-S5-00123"));
    }

    @Test
    public void testObserverRegisteredWithRegistryReceivesUpdatesFromNewOrder() {
        CountingObserver observer = new CountingObserver();
        registry.addObserver(observer);

        RepairOrder order = registry.createRepairOrder(
                new ProblemDescription("Brakes don't work."), anna);
        order.addDiagnosticResult(new DiagnosticResult("Worn pads."));

        assertEquals(1, observer.updates,
                "The observer registered with the registry must be notified " +
                "of updates on newly created orders.");
    }

    @Test
    public void testMultipleObserversAllAttachedToNewOrders() {
        CountingObserver first = new CountingObserver();
        CountingObserver second = new CountingObserver();
        registry.addObserver(first);
        registry.addObserver(second);

        RepairOrder order = registry.createRepairOrder(
                new ProblemDescription("Brakes don't work."), anna);
        order.accept();

        assertEquals(1, first.updates,
                "First observer must be notified.");
        assertEquals(1, second.updates,
                "Second observer must also be notified.");
    }
}
