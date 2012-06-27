package com.dhemery.victor.device.local;

import com.dhemery.victor.os.OSCommand;
import com.dhemery.victor.os.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Interacts with an iOS simulator owned by Victor.
 * The {@link #start()} method launches the simulator.
 * The {@link #stop()} method shuts it down.
 *
 * @author Dale Emery
 */
public class VictorSimulatorProcess implements Service {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final List<String> arguments = new ArrayList<String>();
    private Process simulatorProcess;
    private final String simulatorBinaryPath;
    private final String simulatedProcessName;

    /**
     * Launch the simulator without specifying an application to run.
     *
     * @param simulatorBinaryPath the path to the Simulator executable on this computer.
     */
    public VictorSimulatorProcess(String simulatorBinaryPath) {
        this(simulatorBinaryPath, null);
    }

    private VictorSimulatorProcess(String simulatorBinaryPath, String applicationBinaryPath) {
        this.simulatorBinaryPath = simulatorBinaryPath;
        simulatedProcessName = applicationBinaryPath == null ? null : new File(applicationBinaryPath).getName();
    }

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation.
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer.
     * @param applicationBinaryPath the path to the executable for the application to run.
     * @param deviceType            the kind of device to simulate. See the iOS Simulator's Device menu for possible values.
     */
    public VictorSimulatorProcess(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType) {
        this(simulatorBinaryPath, applicationBinaryPath);
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
        simulatorProcess = command.run();
    }

    @Override
    public void stop() {
        killSimulator();
        killApplication();
    }

    private void killApplication() {
        if(simulatedProcessName == null) return;
        kill(simulatedProcessName);
    }

    private void killSimulator() {
        if (simulatorProcess == null) return;
        kill("iPhone Simulator");
        simulatorProcess = null;
    }

    private static void kill(String processName) {
        new OSCommand("killall", Arrays.asList(processName)).run();
    }
}
