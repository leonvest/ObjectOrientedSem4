package se.kth.iv1350.bikerepair.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.bikerepair.model.PhoneNumber;

/**
 * Unit tests for {@link ErrorLogger}. Verifies that logged exceptions
 * are written to the underlying file with both the exception class name
 * and its message.
 */
public class ErrorLoggerTest {
    private Path logFile;

    @BeforeEach
    public void setUp() throws IOException {
        logFile = Files.createTempFile("bikerepair-errors-test", ".log");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(logFile);
    }

    @Test
    public void testLogExceptionWritesClassNameAndMessage() throws IOException {
        ErrorLogger logger = new ErrorLogger(logFile.toString());
        DatabaseFailureException ex =
                new DatabaseFailureException(new PhoneNumber("0700000000"));
        logger.logException(ex);
        logger.close();

        String contents = Files.readString(logFile);
        assertTrue(contents.contains(DatabaseFailureException.class.getName()),
                "The log file must contain the exception's fully-qualified class name.");
        assertTrue(contents.contains("0700000000"),
                "The log file must contain the exception's message details.");
    }

    @Test
    public void testLogExceptionAppendsAcrossMultipleCalls() throws IOException {
        ErrorLogger logger = new ErrorLogger(logFile.toString());
        logger.logException(new DatabaseFailureException(new PhoneNumber("0700000000")));
        logger.logException(new DatabaseFailureException(new PhoneNumber("0700000001")));
        logger.close();

        String contents = Files.readString(logFile);
        assertTrue(contents.contains("0700000000")
                        && contents.contains("0700000001"),
                "Both exceptions must appear in the log file after multiple calls.");
    }
}
