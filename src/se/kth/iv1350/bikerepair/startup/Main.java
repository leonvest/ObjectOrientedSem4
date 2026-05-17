package se.kth.iv1350.bikerepair.startup;

import java.io.IOException;
import se.kth.iv1350.bikerepair.controller.Controller;
import se.kth.iv1350.bikerepair.integration.ErrorLogger;
import se.kth.iv1350.bikerepair.integration.Printer;
import se.kth.iv1350.bikerepair.integration.RegistryCreator;
import se.kth.iv1350.bikerepair.integration.RepairOrderLogger;
import se.kth.iv1350.bikerepair.view.RepairOrderView;
import se.kth.iv1350.bikerepair.view.View;

/**
 * The application's entry point. Wires together the integration, controller,
 * and view layers, then runs the hard-coded basic flow.
 */
public class Main {

    /**
     * Starts the application.
     *
     * @param args command-line arguments (not used)
     * @throws IOException if a log file cannot be opened
     */
    public static void main(String[] args) throws IOException {
        RegistryCreator registryCreator = RegistryCreator.getInstance();
        Printer printer = new Printer();
        ErrorLogger errorLogger = new ErrorLogger();
        Controller controller = new Controller(registryCreator, printer);

        RepairOrderView liveView = new RepairOrderView();
        RepairOrderLogger orderLogger = new RepairOrderLogger();
        registryCreator.getRepairOrderRegistry().addObserver(liveView);
        registryCreator.getRepairOrderRegistry().addObserver(orderLogger);

        View view = new View(controller, errorLogger);
        view.runBasicFlow();

        orderLogger.close();
        errorLogger.close();
    }
}
