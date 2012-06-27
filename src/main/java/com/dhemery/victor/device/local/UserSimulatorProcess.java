package com.dhemery.victor.device.local;

import com.dhemery.victor.os.Service;

/**
 * Interacts with an iOS simulator owned (i.e. started and stopped) by the user.
 * The {@link #start()} and {@link #stop()} methods do nothing.
 *
 * @author Dale Emery
 */
public class UserSimulatorProcess implements Service {
    @Override public void start() {}
    @Override public void stop() {}
}
