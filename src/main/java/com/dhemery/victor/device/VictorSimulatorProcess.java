package com.dhemery.victor.device;

import com.dhemery.os.Shell;
import com.dhemery.os.ShellCommand;

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
    private final Shell shell;
    private final String simulatedProcessName;
    private final ShellCommand command;

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer
     * @param applicationBinaryPath the path to the executable for the application to run
     * @param deviceType            the kind of device to simulate
     */
    public VictorSimulatorProcess(Shell shell, String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType) {
        this.shell = shell;
        command = new ShellCommand(simulatorBinaryPath)
                .withArguments("-currentSDKRoot", sdkRoot)
                .withArguments("-SimulateDevice", deviceType)
                .withArguments("-SimulateApplication", applicationBinaryPath)
                .describedAs("Start Simulator");
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
        shell.run(new ShellCommand("killall").withArgument(processName).describedAs(commandName));
    }
}
