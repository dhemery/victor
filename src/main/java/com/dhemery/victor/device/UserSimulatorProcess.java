package com.dhemery.victor.device;

/**
 * Interacts with an iOS simulator owned (i.e. started and stopped) by the user.
 * The {@link #start()} and {@link #stop()} methods do nothing.
 */
public class UserSimulatorProcess implements Service {
    /**
     * Does nothing.
     */
    @Override public void start() {}

    /**
     * Does nothing.
     */
    @Override public void stop() {}
}
