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
    private final List<String> baseArguments = new ArrayList<String>();
    private final String simulatorBinaryPath;
    private Process process;

    public LocalSimulator(String sdkRoot, String simulatorBinaryPath) {
        this.simulatorBinaryPath = simulatorBinaryPath;
        baseArguments.add("-currentSdkRoot");
        baseArguments.add(sdkRoot);
    }

    public void startWithApplication(String applicationBinaryPath) {
        List<String> arguments = new ArrayList<String>(baseArguments);
        arguments.add("-SimulateApplication");
        arguments.add(applicationBinaryPath);
        OSCommand command = new OSCommand(simulatorBinaryPath, arguments);
        log.debug("Launching simulator {}", command);
        process = command.run();
    }

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
