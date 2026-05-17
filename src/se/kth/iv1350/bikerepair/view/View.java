package se.kth.iv1350.bikerepair.view;

import se.kth.iv1350.bikerepair.controller.Controller;
import se.kth.iv1350.bikerepair.integration.CustomerDTO;
import se.kth.iv1350.bikerepair.integration.CustomerNotFoundException;
import se.kth.iv1350.bikerepair.integration.DatabaseFailureException;
import se.kth.iv1350.bikerepair.integration.ErrorLogger;
import se.kth.iv1350.bikerepair.integration.RepairOrderDTO;
import se.kth.iv1350.bikerepair.integration.RepairTaskDTO;
import se.kth.iv1350.bikerepair.model.Amount;
import se.kth.iv1350.bikerepair.model.DiagnosticResult;
import se.kth.iv1350.bikerepair.model.LoyaltyDiscount;
import se.kth.iv1350.bikerepair.model.PhoneNumber;
import se.kth.iv1350.bikerepair.model.ProblemDescription;

/**
 * A placeholder for the real user interface. Instead of reading user
 * input, it makes a hard-coded sequence of calls to the controller that
 * exercises the basic flow plus two alternative flows (customer not found
 * and database failure), and prints everything the controller returns.
 */
public class View {
    private static final PhoneNumber CUSTOMER_PHONE = new PhoneNumber("0701234567");
    private static final PhoneNumber UNKNOWN_PHONE = new PhoneNumber("0000000001");
    private static final PhoneNumber DB_FAILURE_PHONE = new PhoneNumber("0700000000");
    private static final ProblemDescription PROBLEM_DESCRIPTION =
            new ProblemDescription("Brakes don't work and battery drains quickly.");
    private static final DiagnosticResult DIAGNOSTIC_FINDING =
            new DiagnosticResult("Worn brake pads and a faulty battery cell.");
    private static final RepairTaskDTO BRAKE_REPAIR_TASK =
            new RepairTaskDTO("Replace brake pads", new Amount(800));
    private static final RepairTaskDTO BATTERY_REPLACEMENT_TASK =
            new RepairTaskDTO("Replace battery", new Amount(2500));

    private final Controller controller;
    private final ErrorLogger errorLogger;

    /**
     * Creates a new view that talks to the given controller and logs
     * unexpected errors via the given logger.
     *
     * @param controller the controller used for all system operations
     * @param errorLogger the logger used to record unexpected errors
     */
    public View(Controller controller, ErrorLogger errorLogger) {
        this.controller = controller;
        this.errorLogger = errorLogger;
    }

    /**
     * Runs the hard-coded basic flow followed by two alternative flows
     * that demonstrate exception handling. Every value returned by the
     * controller is printed to <code>System.out</code>.
     */
    public void runBasicFlow() {
        runHappyPath();
        runUnknownCustomerScenario();
        runDatabaseFailureScenario();
    }

    private void runHappyPath() {
        printHeader("1. Cashier looks up the customer");
        CustomerDTO customer;
        try {
            customer = controller.findCustomer(CUSTOMER_PHONE);
        } catch (CustomerNotFoundException e) {
            System.out.println("No customer registered for phone "
                    + e.getSearchedPhone() + ".");
            return;
        }
        System.out.println(customer);

        printHeader("2. Cashier creates a repair order");
        RepairOrderDTO afterCreation = controller.createRepairOrder(
                PROBLEM_DESCRIPTION, customer);
        System.out.println(afterCreation);

        printHeader("3. Technician records the diagnostic finding");
        RepairOrderDTO afterDiagnosis = controller.addDiagnosticResult(
                DIAGNOSTIC_FINDING);
        System.out.println(afterDiagnosis);

        printHeader("4. Technician adds the brake repair task");
        RepairOrderDTO afterFirstTask = controller.addRepairTask(BRAKE_REPAIR_TASK);
        System.out.println(afterFirstTask);

        printHeader("5. Technician adds the battery replacement task");
        RepairOrderDTO afterSecondTask = controller.addRepairTask(BATTERY_REPLACEMENT_TASK);
        System.out.println(afterSecondTask);

        printHeader("6. Loyalty discount is applied");
        RepairOrderDTO afterDiscount = controller.applyDiscount(new LoyaltyDiscount());
        System.out.println(afterDiscount);

        printHeader("7. Customer accepts the repair");
        RepairOrderDTO accepted = controller.acceptRepairOrder();
        System.out.println(accepted);
    }

    private void runUnknownCustomerScenario() {
        printHeader("Alt flow A: looking up an unknown customer");
        try {
            controller.findCustomer(UNKNOWN_PHONE);
        } catch (CustomerNotFoundException e) {
            System.out.println("No customer is registered for phone "
                    + e.getSearchedPhone()
                    + ". Please ask the customer to register first.");
        }
    }

    private void runDatabaseFailureScenario() {
        printHeader("Alt flow B: simulated database failure");
        try {
            controller.findCustomer(DB_FAILURE_PHONE);
        } catch (CustomerNotFoundException e) {
            System.out.println("No customer registered for phone "
                    + e.getSearchedPhone() + ".");
        } catch (DatabaseFailureException e) {
            System.out.println(
                    "Sorry, a technical problem prevented this operation. "
                    + "The issue has been reported.");
            errorLogger.logException(e);
        }
    }

    private void printHeader(String title) {
        System.out.println();
        System.out.println("--- " + title + " ---");
    }
}
