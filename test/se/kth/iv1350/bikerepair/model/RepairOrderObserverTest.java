package se.kth.iv1350.bikerepair.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.integration.BikeDTO;
import se.kth.iv1350.bikerepair.integration.CustomerDTO;
import se.kth.iv1350.bikerepair.integration.RepairOrderDTO;
import se.kth.iv1350.bikerepair.integration.RepairTaskDTO;

/**
 * Unit tests for the Observer mechanism on {@link RepairOrder}. Uses a
 * stub observer that records every snapshot it receives.
 */
public class RepairOrderObserverTest {

    /**
     * Records every snapshot it receives so the test can inspect them.
     */
    private static class RecordingObserver implements RepairOrderObserver {
        private final List<RepairOrderDTO> received = new ArrayList<>();

        @Override
        public void updated(RepairOrderDTO order) {
            received.add(order);
        }
    }

    private RepairOrder order;
    private RecordingObserver observer;

    @BeforeEach
    public void setUp() {
        CustomerDTO customer = new CustomerDTO(
                "Anna Andersson",
                "anna@example.com",
                new PhoneNumber("0701234567"),
                new BikeDTO("VanMoof", "S5", "VM-S5-00123"));
        order = new RepairOrder(
                new OrderId(1),
                new ProblemDescription("Brakes don't work."),
                customer);
        observer = new RecordingObserver();
        order.addObserver(observer);
    }

    @Test
    public void testObserverIsNotifiedAfterAddDiagnosticResult() {
        order.addDiagnosticResult(new DiagnosticResult("Worn brake pads."));
        assertEquals(1, observer.received.size(),
                "The observer must be notified once after addDiagnosticResult.");
    }

    @Test
    public void testObserverReceivesUpdatedStateAfterAddDiagnosticResult() {
        order.addDiagnosticResult(new DiagnosticResult("Worn brake pads."));
        RepairOrderDTO snapshot = observer.received.get(0);
        assertEquals(RepairOrderState.AWAITING_APPROVAL, snapshot.getState(),
                "The observer's snapshot must reflect the new state.");
    }

    @Test
    public void testObserverIsNotifiedAfterAddRepairTask() {
        order.addRepairTask(new RepairTaskDTO("Replace brake pads", new Amount(800)));
        assertEquals(1, observer.received.size(),
                "The observer must be notified once after addRepairTask.");
    }

    @Test
    public void testObserverReceivesUpdatedTotalCostAfterAddRepairTask() {
        order.addRepairTask(new RepairTaskDTO("Replace brake pads", new Amount(800)));
        RepairOrderDTO snapshot = observer.received.get(0);
        assertEquals(new Amount(800), snapshot.getTotalCost(),
                "The observer's snapshot must include the new total cost.");
    }

    @Test
    public void testObserverIsNotifiedAfterAccept() {
        order.accept();
        assertEquals(1, observer.received.size(),
                "The observer must be notified once after accept.");
    }

    @Test
    public void testObserverReceivesAcceptedStateAfterAccept() {
        order.accept();
        RepairOrderDTO snapshot = observer.received.get(0);
        assertEquals(RepairOrderState.ACCEPTED, snapshot.getState(),
                "The observer's snapshot must reflect the ACCEPTED state.");
    }

    @Test
    public void testObserverIsNotifiedAfterSetDiscountStrategy() {
        order.setDiscountStrategy(new LoyaltyDiscount());
        assertEquals(1, observer.received.size(),
                "The observer must be notified after a discount is set.");
    }

    @Test
    public void testMultipleObserversAllGetNotified() {
        RecordingObserver second = new RecordingObserver();
        order.addObserver(second);
        order.accept();
        assertTrue(observer.received.size() == 1 && second.received.size() == 1,
                "All registered observers must be notified on every update.");
    }
}
