package com.dhemery.victor.device;

import com.dhemery.osx.ScriptableApplication;
import com.dhemery.os.Shell;

/**
 * Interacts with a running simulator.
 */
public class SimulatorApplication extends ScriptableApplication {
    public SimulatorApplication(Shell shell) {
        super("iPhone Simulator", "iOS Simulator", shell);
    }
}
