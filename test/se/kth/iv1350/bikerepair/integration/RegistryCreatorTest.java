package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link RegistryCreator}. Verifies the Singleton
 * implementation: a single shared instance, and consistent registry
 * references.
 */
public class RegistryCreatorTest {
    private RegistryCreator creator;

    @BeforeEach
    public void setUp() {
        creator = RegistryCreator.getInstance();
    }

    @AfterEach
    public void tearDown() {
        creator = null;
    }

    @Test
    public void testGetInstanceNeverReturnsNull() {
        assertNotNull(RegistryCreator.getInstance(),
                "getInstance must return a non-null instance.");
    }

    @Test
    public void testGetInstanceReturnsTheSameInstanceOnRepeatedCalls() {
        assertSame(RegistryCreator.getInstance(), RegistryCreator.getInstance(),
                "getInstance must always return the same instance (Singleton).");
    }

    @Test
    public void testGetCustomerRegistryReturnsNonNull() {
        assertNotNull(creator.getCustomerRegistry(),
                "getCustomerRegistry must return a non-null registry.");
    }

    @Test
    public void testGetRepairOrderRegistryReturnsNonNull() {
        assertNotNull(creator.getRepairOrderRegistry(),
                "getRepairOrderRegistry must return a non-null registry.");
    }

    @Test
    public void testGetCustomerRegistryReturnsSameInstanceOnRepeatedCalls() {
        assertSame(creator.getCustomerRegistry(), creator.getCustomerRegistry(),
                "getCustomerRegistry must always return the same instance.");
    }

    @Test
    public void testGetRepairOrderRegistryReturnsSameInstanceOnRepeatedCalls() {
        assertSame(creator.getRepairOrderRegistry(), creator.getRepairOrderRegistry(),
                "getRepairOrderRegistry must always return the same instance.");
    }
}
