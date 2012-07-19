package com.dhemery.victor.device;

import com.dhemery.os.*;
import com.dhemery.os.PublishingCommandBuilder;

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
    private final OSCommandSubscriber publisher;

    /**
     * @param sdkRoot               the path to the SDK to use for the simulation
     * @param simulatorBinaryPath   the path to the Simulator executable on this computer
     * @param applicationBinaryPath the path to the executable for the application to run
     * @param deviceType            the kind of device to simulate
     */
    public VictorSimulatorProcess(String sdkRoot, String simulatorBinaryPath, String applicationBinaryPath, String deviceType, OSCommandSubscriber publisher) {
        this.publisher = publisher;
        command = new PublishingCommandBuilder(publisher, "Start Simulator", simulatorBinaryPath)
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
        command.run();
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
        new PublishingCommandBuilder(publisher, commandName, "killall")
                .withArgument(processName)
                .build()
                .run();
    }
}
