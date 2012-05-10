package com.dhemery.victor.device;

import com.dhemery.victor.device.local.OSCommand;
import com.dhemery.victor.device.local.TouchMenuItemCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Interacts with an iOS simulator owned (i.e. started and stopped) by the user.
 * The {@link #start()} and {@link #stop()} methods do nothing.
 *
 * @author Dale Emery
 */
public class UserSimulatorAgent implements SimulatorAgent {
    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public void touchMenuItem(String menuName, String menuItemName) {
        new TouchMenuItemCommand(menuName, menuItemName).run();
    }
}