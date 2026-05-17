package se.kth.iv1350.bikerepair.integration;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import se.kth.iv1350.bikerepair.model.RepairOrderObserver;

/**
 * An observer that appends repair-order updates to a log file. Used to
 * notify technicians of new and changed repair orders, as required by
 * the Observer-pattern part of the task. Never calls the controller or
 * any other application code.
 */
public class RepairOrderLogger implements RepairOrderObserver {
    private static final String DEFAULT_LOG_FILE = "repair-orders.log";

    private final PrintWriter logFile;

    /**
     * Creates a new logger writing to <code>repair-orders.log</code> in
     * the current working directory.
     *
     * @throws IOException if the log file cannot be opened
     */
    public RepairOrderLogger() throws IOException {
        this(DEFAULT_LOG_FILE);
    }

    /**
     * Creates a new logger writing to the specified file.
     *
     * @param fileName the path of the file to write to
     * @throws IOException if the file cannot be opened
     */
    public RepairOrderLogger(String fileName) throws IOException {
        this.logFile = new PrintWriter(
                Files.newBufferedWriter(
                        Path.of(fileName),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND));
    }

    /**
     * Writes a timestamped snapshot of the updated order to the log file.
     *
     * @param order the updated order
     */
    @Override
    public void updated(RepairOrderDTO order) {
        logFile.println("---------------------------------------------");
        logFile.println(LocalDateTime.now());
        logFile.println(order);
        logFile.flush();
    }

    /**
     * Closes the log file. Any further calls to
     * {@link #updated(RepairOrderDTO)} will fail.
     */
    public void close() {
        logFile.close();
    }
}
