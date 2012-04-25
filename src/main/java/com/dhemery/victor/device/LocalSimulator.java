package com.dhemery.victor.device;

import com.dhemery.victor.device.local.OSCommand;
import com.dhemery.victor.device.local.TouchMenuItemCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
public class LocalSimulator implements Simulator {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<String> arguments = new ArrayList<String>();
    private final String simulatorBinaryPath;
    private Process process;

    /**
     * @param sdkRoot the path to the SDK to use for the simulation.
     * @param simulatorBinaryPath the path to the Simulator executable on this computer.
     */
    public LocalSimulator(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath) {
        this.simulatorBinaryPath = simulatorBinaryPath;
        arguments.add("-currentSDKRoot");
        arguments.add(sdkRoot);
        arguments.add("-SimulateApplication");
        arguments.add(applicationBinaryPath);
    }

    @Override
    public void start() {
        OSCommand command = new OSCommand(simulatorBinaryPath, arguments);
        log.debug("Launching simulator {}", command);
        process = command.run();
    }

    @Override
    public void stop() {
        log.debug("Stopping simulator by touching Quit menu item");
        touchMenuItem("iOS Simulator", "Quit iOS Simulator");
        try {
            process.waitFor();
        } catch (InterruptedException cause) {
            throw new RuntimeException("Interrupted while waiting for simulator process to stop.", cause);
        }
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        new TouchMenuItemCommand(menuName, menuItemName).run();
    }
}
