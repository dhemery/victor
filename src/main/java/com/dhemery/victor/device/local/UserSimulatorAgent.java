package com.dhemery.victor.device.local;

import com.dhemery.victor.device.SimulatorAgent;

/**
 * Interacts with an iOS simulator owned (i.e. started and stopped) by the user.
 * The {@link #start()} and {@link #stop()} methods do nothing.
 *
 * @author Dale Emery
 */
public class UserSimulatorAgent implements SimulatorAgent {
    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        new TouchMenuItemCommand(menuName, menuItemName).run();
    }
}
