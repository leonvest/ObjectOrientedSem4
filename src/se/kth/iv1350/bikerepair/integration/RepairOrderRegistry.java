package se.kth.iv1350.bikerepair.integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import se.kth.iv1350.bikerepair.model.OrderId;
import se.kth.iv1350.bikerepair.model.ProblemDescription;
import se.kth.iv1350.bikerepair.model.RepairOrder;
import se.kth.iv1350.bikerepair.model.RepairOrderObserver;

/**
 * Stands in for the external repair-order database. Holds repair orders
 * in memory, assigns them sequential ids, and acts as a propagator for
 * {@link RepairOrderObserver}s — observers registered with the registry
 * are automatically attached to every order it creates.
 */
public class RepairOrderRegistry {
    private static final int FIRST_ORDER_ID = 1;

    private final List<RepairOrder> orders = new ArrayList<>();
    private final List<RepairOrderObserver> observers = new ArrayList<>();
    private int nextOrderId = FIRST_ORDER_ID;

    /**
     * Registers an observer that will be attached to every repair order
     * created by this registry from now on. Existing orders are not
     * affected.
     *
     * @param observer the observer to register
     */
    public void addObserver(RepairOrderObserver observer) {
        observers.add(observer);
    }

    /**
     * Creates a new repair order, stores it, attaches every registered
     * observer to it, and returns it.
     *
     * @param problemDescription the customer's description of the problem
     * @param customer the customer who owns the bike
     * @return the newly created order
     */
    public RepairOrder createRepairOrder(ProblemDescription problemDescription, CustomerDTO customer) {
        RepairOrder order = new RepairOrder(new OrderId(nextOrderId), problemDescription, customer);
        nextOrderId++;
        for (RepairOrderObserver observer : observers) {
            order.addObserver(observer);
        }
        orders.add(order);
        return order;
    }

    /**
     * @return an unmodifiable view of all stored repair orders
     */
    public List<RepairOrder> findAll() {
        return Collections.unmodifiableList(orders);
    }
}
