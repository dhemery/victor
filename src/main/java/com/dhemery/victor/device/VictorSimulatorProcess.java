package com.dhemery.victor.device;

import com.dhemery.os.RunnableCommand;
import com.dhemery.os.Shell;

import java.io.File;

/**
 * Interacts with an iOS simulator process owned by Victor.
 * The {@link #start()} method launches the simulator.
 * The {@link #stop()} method shuts it down.
 *
 * @author Dale Emery
 */
public class VictorSimulatorProcess implements Service {
    private final RunnableCommand startSimulator;
    private final RunnableCommand killSimulatorProcess;
    private final RunnableCommand killSimulatedApplicationProcess;

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer
     * @param applicationBinaryPath the path to the executable for the application to run
     * @param deviceType            the kind of device to simulate
     */
    public VictorSimulatorProcess(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType, Shell shell) {
        startSimulator = shell.command("Start Simulator", simulatorBinaryPath)
                .withArguments("-currentSDKRoot", sdkRoot)
                .withArguments("-SimulateDevice", deviceType)
                .withArguments("-SimulateApplication", applicationBinaryPath)
                .build();
        killSimulatorProcess = killCommand(shell, "iPhone Simulator");
        String simulatedProcessName = new File(applicationBinaryPath).getName();
        killSimulatedApplicationProcess = killCommand(shell, simulatedProcessName);
    }

    /**
     * Start the simulator.
     */
    @Override
    public void start() {
        startSimulator.run();
    }

    /**
     * Stop the simulator.
     */
    @Override
    public void stop() {
        killSimulatorProcess.run();
        killSimulatedApplicationProcess.run();
    }

    private RunnableCommand killCommand(Shell shell, String processName) {
        return shell.command("Kill " + processName, "killall")
                .withArgument(processName)
                .build();
    }
}
