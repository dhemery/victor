package com.dhemery.victor.device.local;

import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.os.OSCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Interacts with an iOS simulator owned by Victor.
 * The {@link #start()} method launches the simulator.
 * The {@link #stop()} method shuts it down.
 *
 * @author Dale Emery
 */
public class VictorSimulatorAgent implements SimulatorAgent {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<String> arguments = new ArrayList<String>();
    private final String simulatorBinaryPath;
    @SuppressWarnings("InstanceVariableMayNotBeInitialized")
    private Process process;

    /**
     * Launch the simulator without specifying an application to run.
     *
     * @param simulatorBinaryPath the path to the Simulator executable on this computer.
     */
    public VictorSimulatorAgent(String simulatorBinaryPath) {
        this.simulatorBinaryPath = simulatorBinaryPath;
    }

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation.
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer.
     * @param applicationBinaryPath the path to the executable for the application to run.
     * @param deviceType            the kind of device to simulate. See the iOS Simulator's Device menu for possible values.
     */
    public VictorSimulatorAgent(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType) {
        this(simulatorBinaryPath);
        arguments.add("-currentSDKRoot");
        arguments.add(sdkRoot);
        arguments.add("-SimulateDevice");
        arguments.add(deviceType);
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
        if (process == null) return;
        log.debug("Stopping simulator by touching Quit menu item");
        touchMenuItem("iOS Simulator", "Quit iOS Simulator");
        try {
            process.waitFor();
        } catch (InterruptedException cause) {
            throw new SimulatorException("Interrupted while waiting for simulator process to stop.", cause);
        }
        //noinspection AssignmentToNull
        process = null;
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        new TouchMenuItemCommand(menuName, menuItemName).run();
    }
}
