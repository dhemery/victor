package com.dhemery.victor.device;

import com.dhemery.os.*;
import com.dhemery.os.OSCommandBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Interacts with an iOS simulator process owned by Victor.
 * The {@link #start()} method launches the simulator.
 * The {@link #stop()} method shuts it down.
 *
 * @author Dale Emery
 */
public class VictorSimulatorProcess implements Service {
    private final List<String> arguments = new ArrayList<String>();
    private final String simulatedProcessName;
    private final OSCommand command;
    private final Shell shell;

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer
     * @param applicationBinaryPath the path to the executable for the application to run
     * @param deviceType            the kind of device to simulate
     */
    public VictorSimulatorProcess(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType, Shell shell) {
        this.shell = shell;
        command = new OSCommandBuilder("Start Simulator", simulatorBinaryPath)
                .withArguments("-currentSDKRoot", sdkRoot)
                .withArguments("-SimulateDevice", deviceType)
                .withArguments("-SimulateApplication", applicationBinaryPath)
                .build();
        simulatedProcessName = new File(applicationBinaryPath).getName();
    }

    /**
     * Start the simulator.
     */
    @Override
    public void start() {
        shell.run(command);
    }

    /**
     * Stop the simulator.
     */
    @Override
    public void stop() {
        killSimulator();
        killApplication();
    }

    private void killApplication() {
        kill(simulatedProcessName);
    }

    private void killSimulator() {
        kill("iPhone Simulator");
    }

    private void kill(String processName) {
        String commandName = "Kill " + processName;
        OSCommand command = new OSCommandBuilder(commandName, "killall")
                .withArgument(processName)
                .build();
        shell.run(command);
    }
}
