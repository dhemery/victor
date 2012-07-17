package com.dhemery.victor.device;

import com.dhemery.osx.ScriptableApplication;
import com.dhemery.os.Shell;

public class SimulatorApplication extends ScriptableApplication {
    public SimulatorApplication(Shell shell) {
        super(shell, "iPhone Simulator", "iOS Simulator");
    }
}
