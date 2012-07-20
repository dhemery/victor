package com.dhemery.victor.device;

import com.dhemery.os.Shell;
import com.dhemery.osx.ScriptableApplication;

/**
 * Interacts with a running simulator.
 */
public class SimulatorApplication extends ScriptableApplication {
    public SimulatorApplication(Shell shell) {
        super("iPhone Simulator", "iOS Simulator", shell);
    }
}
