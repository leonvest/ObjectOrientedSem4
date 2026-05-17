package se.kth.iv1350.bikerepair.integration;

/**
 * Creates and provides access to all integration-layer registries used
 * by the controller. Implemented as a Singleton so that the entire
 * application shares one set of registries; the in-memory state in the
 * registries would otherwise be lost if a second creator were
 * instantiated.
 */
public class RegistryCreator {
    private static final RegistryCreator INSTANCE = new RegistryCreator();

    private final CustomerRegistry customerRegistry = new CustomerRegistry();
    private final RepairOrderRegistry repairOrderRegistry = new RepairOrderRegistry();

    private RegistryCreator() {
        // private to enforce Singleton access via getInstance()
    }

    /**
     * @return the single instance of <code>RegistryCreator</code>
     */
    public static RegistryCreator getInstance() {
        return INSTANCE;
    }

    /**
     * @return the registry of customers
     */
    public CustomerRegistry getCustomerRegistry() {
        return customerRegistry;
    }

    /**
     * @return the registry of repair orders
     */
    public RepairOrderRegistry getRepairOrderRegistry() {
        return repairOrderRegistry;
    }
}
