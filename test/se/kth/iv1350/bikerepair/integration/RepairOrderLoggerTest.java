package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.model.Amount;
import se.kth.iv1350.bikerepair.model.DiagnosticResult;
import se.kth.iv1350.bikerepair.model.OrderId;
import se.kth.iv1350.bikerepair.model.PhoneNumber;
import se.kth.iv1350.bikerepair.model.ProblemDescription;
import se.kth.iv1350.bikerepair.model.RepairOrderState;

/**
 * Unit tests for {@link RepairOrderLogger}. Verifies that order updates
 * are appended to the underlying file.
 */
public class RepairOrderLoggerTest {
    private Path logFile;
    private RepairOrderDTO sampleOrder;

    @BeforeEach
    public void setUp() throws IOException {
        logFile = Files.createTempFile("repair-orders-test", ".log");

        CustomerDTO customer = new CustomerDTO(
                "Anna Andersson",
                "anna@example.com",
                new PhoneNumber("0701234567"),
                new BikeDTO("VanMoof", "S5", "VM-S5-00123"));
        List<DiagnosticResult> diagnostics = new ArrayList<>(Arrays.asList(
                new DiagnosticResult("Worn brake pads.")));
        List<RepairTaskDTO> tasks = new ArrayList<>(Arrays.asList(
                new RepairTaskDTO("Replace brake pads", new Amount(800))));
        sampleOrder = new RepairOrderDTO(
                new OrderId(42),
                LocalDate.of(2026, 1, 15),
                new ProblemDescription("Brakes don't work."),
                RepairOrderState.AWAITING_APPROVAL,
                customer,
                diagnostics,
                tasks,
                new Amount(800));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(logFile);
    }

    @Test
    public void testUpdatedWritesTheOrderToTheLogFile() throws IOException {
        RepairOrderLogger logger = new RepairOrderLogger(logFile.toString());
        logger.updated(sampleOrder);
        logger.close();

        String contents = Files.readString(logFile);
        assertTrue(contents.contains("Order #42"),
                "The log file must contain the order id.");
        assertTrue(contents.contains("Replace brake pads"),
                "The log file must contain the task description.");
    }

    @Test
    public void testUpdatedAppendsAcrossMultipleCalls() throws IOException {
        RepairOrderLogger logger = new RepairOrderLogger(logFile.toString());
        logger.updated(sampleOrder);
        logger.updated(sampleOrder);
        logger.close();

        String contents = Files.readString(logFile);
        int firstOccurrence = contents.indexOf("Order #42");
        int secondOccurrence = contents.indexOf("Order #42", firstOccurrence + 1);
        assertTrue(secondOccurrence > firstOccurrence,
                "Multiple calls to updated must each append to the log file.");
    }
}
