package com.dhemery.victor.device;

import com.dhemery.os.OSCommandSubscriber;
import com.dhemery.osx.ScriptableApplication;

/**
 * Interacts with a running simulator.
 */
public class SimulatorApplication extends ScriptableApplication {
    public SimulatorApplication(OSCommandSubscriber publisher) {
        super("iPhone Simulator", "iOS Simulator", publisher);
    }
}
