package com.dhemery.victor.device;

import com.dhemery.osx.AppleScriptShell;
import com.dhemery.osx.ScriptableApplication;

/**
 * Interacts with a running simulator.
 */
public class SimulatorApplication extends ScriptableApplication {
    public SimulatorApplication(AppleScriptShell shell) {
        super("iPhone Simulator", "iOS Simulator", shell);
    }
}
