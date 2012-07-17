package com.dhemery.victor.device;

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
