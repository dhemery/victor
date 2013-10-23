package com.dhemery.victor.device;

import com.dhemery.os.Command;
import com.dhemery.os.Shell;

import java.io.File;

import static com.dhemery.os.CommandBuilder.command;

/**
 * Interacts with an iOS simulator process owned by Victor.
 * The {@link #start()} method launches the simulator.
 * The {@link #stop()} method shuts it down.
 *
 * <p><strong>NOTE:</strong>
 * This class works only with Xcode 4.
 * For Xcode 5, see {@link InstrumentsSimulatorProcess}
 *
 * @author Dale Emery
 */
public class VictorSimulatorProcess implements Service {
    private final Command startSimulator;
    private final Command stopSimulator;
    private final Command stopSimulatedApplication;
    private final Shell shell;

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer
     * @param applicationBinaryPath the path to the executable for the application to run
     * @param deviceType            the kind of device to simulate
     */
    public VictorSimulatorProcess(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType, Shell shell) {
        this.shell = shell;
        startSimulator = startSimulatorCommand(sdkRoot, simulatorBinaryPath, applicationBinaryPath, deviceType);
        stopSimulator = killCommand("Stop Simulator", "iPhone Simulator");
        String simulatedProcessName = new File(applicationBinaryPath).getName();
        stopSimulatedApplication = killCommand("Stop Simulated Application", simulatedProcessName);
    }

    @Override
    public void start() {
        run(startSimulator);
    }

    private void run(Command... commands) {
        for(Command command : commands) shell.run(command);
    }

    @Override
    public void stop() {
        run(stopSimulator, stopSimulatedApplication);
    }

    private static Command killCommand(String description, String processName) {
        return command(description, "killall")
                .withArgument(processName)
                .build();
    }

    private static Command startSimulatorCommand(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType) {
        return command("Start Simulator", simulatorBinaryPath)
                .withArguments("-currentSDKRoot", sdkRoot)
                .withArguments("-SimulateDevice", deviceType)
                .withArguments("-SimulateApplication", applicationBinaryPath)
                .build();
    }
}
