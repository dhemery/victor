package com.dhemery.victor.simulator.local;

import com.dhemery.victor.simulator.Simulator;
import com.dhemery.victor.simulator.SimulatorLaunchCommand;

/**
 * An iOS simulator running on this computer.
 * @author Dale Emery
 *
 */
// todo Configure to remove dependence on fixed paths.
public class LocalSimulator implements Simulator {
    private static final String RUN_HEADLESS = "-RegisterForSystemEvents";
    private Process simulatorProcess;
    private final SimulatorLaunchCommand launchCommand;

    public LocalSimulator(SimulatorLaunchCommand launchCommand) {
        this.launchCommand = launchCommand;
    }

    public void launch() {
        simulatorProcess = launchCommand.launchCommand().run();
    }

    @Override
    public void shutDown() {
        if(simulatorProcess == null) return;
        if(launchCommand.isRunningHeadless()) {
            simulatorProcess.destroy();
        } else {
            touchMenuItem("iOS Simulator", "Quit iOS Simulator");
            waitForSimulatorToShutDown();
        }
        simulatorProcess = null;
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        new TouchMenuItemCommand(menuName, menuItemName).run();
    }

    private void waitForSimulatorToShutDown() {
        try {
            simulatorProcess.waitFor();
        } catch (InterruptedException e) {
            throw new SimulatorCommandException("Exception while waiting for simulator to shut down", e);
        }
    }
}
