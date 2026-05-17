package se.kth.iv1350.bikerepair.integration;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/**
 * Writes exception information to a log file. Used to notify developers
 * when the program is not functioning as intended. The file is opened
 * for append, so log entries accumulate across runs.
 */
public class ErrorLogger {
    private static final String DEFAULT_LOG_FILE = "bikerepair-errors.log";

    private final PrintWriter logFile;

    /**
     * Creates a new error logger writing to <code>bikerepair-errors.log</code>
     * in the current working directory.
     *
     * @throws IOException if the log file cannot be opened
     */
    public ErrorLogger() throws IOException {
        this(DEFAULT_LOG_FILE);
    }

    /**
     * Creates a new error logger writing to the specified file.
     *
     * @param fileName the path of the file to write to
     * @throws IOException if the file cannot be opened
     */
    public ErrorLogger(String fileName) throws IOException {
        this.logFile = new PrintWriter(
                Files.newBufferedWriter(
                        Path.of(fileName),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND));
    }

    /**
     * Writes a timestamped entry describing the specified exception.
     * The entry includes the exception's class name, message and
     * complete stack trace.
     *
     * @param exception the exception to log
     */
    public void logException(Exception exception) {
        logFile.println("---------------------------------------------");
        logFile.print(LocalDateTime.now());
        logFile.print(" ");
        logFile.print(exception.getClass().getName());
        logFile.print(": ");
        logFile.println(exception.getMessage());
        exception.printStackTrace(logFile);
        logFile.flush();
    }

    /**
     * Closes the log file. Any further calls to
     * {@link #logException(Exception)} will fail.
     */
    public void close() {
        logFile.close();
    }
}
