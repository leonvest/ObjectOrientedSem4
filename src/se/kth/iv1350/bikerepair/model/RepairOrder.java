package se.kth.iv1350.bikerepair.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.bikerepair.integration.CustomerDTO;
import se.kth.iv1350.bikerepair.integration.Printer;
import se.kth.iv1350.bikerepair.integration.RepairOrderDTO;
import se.kth.iv1350.bikerepair.integration.RepairTaskDTO;

/**
 * Repair order describes a customer's request to repair their electric bike.
 * It tracks the diagnostic findings, the planned repair tasks, the chosen
 * discount strategy, the lifecycle state, and notifies its registered
 * observers whenever any of these change.
 */
public class RepairOrder {

    private final OrderId id;
    private final LocalDate creationDate;
    private final ProblemDescription problemDescription;
    private final CustomerDTO customer;
    private final List<DiagnosticResult> diagnosticResults = new ArrayList<>();
    private final List<RepairTask> repairTasks = new ArrayList<>();
    private final List<RepairOrderObserver> observers = new ArrayList<>();
    private RepairOrderState state;
    private DiscountStrategy discountStrategy = new NoDiscount();

    /**
     * Creates a new repair order in the {@link RepairOrderState#CREATED} state.
     *
     * @param id the unique id
     * @param problemDescription the customer's description of the problem
     * @param customer the customer who owns the bike
     */
    public RepairOrder(OrderId id, ProblemDescription problemDescription, CustomerDTO customer) {
        this.id = id;
        this.creationDate = LocalDate.now();
        this.problemDescription = problemDescription;
        this.customer = customer;
        this.state = RepairOrderState.CREATED;
    }

    /**
     * Registers an observer that will be notified whenever this order
     * is updated.
     *
     * @param observer the observer to register
     */
    public void addObserver(RepairOrderObserver observer) {
        observers.add(observer);
    }

    /**
     * Records a diagnostic finding and moves the order to
     * {@link RepairOrderState#AWAITING_APPROVAL}. All registered
     * observers are then notified.
     *
     * @param result the diagnostic finding
     */
    public void addDiagnosticResult(DiagnosticResult result) {
        diagnosticResults.add(result);
        state = RepairOrderState.AWAITING_APPROVAL;
        notifyObservers();
    }

    /**
     * Adds a planned repair task and moves the order to
     * {@link RepairOrderState#AWAITING_APPROVAL}. All registered
     * observers are then notified.
     *
     * @param task a description and cost of the planned repair task
     */
    public void addRepairTask(RepairTaskDTO task) {
        repairTasks.add(new RepairTask(task.getDescription(), task.getCost()));
        state = RepairOrderState.AWAITING_APPROVAL;
        notifyObservers();
    }

    /**
     * Marks this order as accepted by the customer and notifies all
     * registered observers.
     */
    public void accept() {
        state = RepairOrderState.ACCEPTED;
        notifyObservers();
    }

    /**
     * Replaces this order's discount strategy and notifies all registered
     * observers, since the displayed total cost will change.
     *
     * @param discountStrategy the new discount strategy
     */
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
        notifyObservers();
    }

    /**
     * Creates a {@link Receipt} for this order and sends it to the
     * specified printer.
     *
     * @param printer the printer that will print the receipt
     */
    public void printReceipt(Printer printer) {
        Receipt receipt = new Receipt(toDTO());
        printer.printReceipt(receipt);
    }

    /** @return the unique id of this order */
    public OrderId getId() {
        return id;
    }

    /** @return the customer who owns the bike */
    public CustomerDTO getCustomer() {
        return customer;
    }

    /** @return the current state of this order */
    public RepairOrderState getState() {
        return state;
    }

    /**
     * Calculates the total cost of all repair tasks in this order, after
     * applying the current discount strategy.
     *
     * @return the discounted total cost
     */
    public Amount getTotalCost() {
        Amount total = new Amount();
        for (RepairTask task : repairTasks) {
            total = total.plus(task.getCost());
        }
        return discountStrategy.applyTo(total);
    }

    /**
     * Creates an immutable snapshot of this order for use outside the model layer.
     *
     * @return a DTO describing this order
     */
    public RepairOrderDTO toDTO() {
        List<RepairTaskDTO> taskDTOs = new ArrayList<>();
        for (RepairTask task : repairTasks) {
            taskDTOs.add(new RepairTaskDTO(task.getDescription(), task.getCost()));
        }
        return new RepairOrderDTO(
                id, creationDate, problemDescription, state, customer,
                new ArrayList<>(diagnosticResults), taskDTOs, getTotalCost());
    }

    private void notifyObservers() {
        RepairOrderDTO snapshot = toDTO();
        for (RepairOrderObserver observer : observers) {
            observer.updated(snapshot);
        }
    }
}
