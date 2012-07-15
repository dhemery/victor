package com.dhemery.victor.device;

import com.dhemery.victor.os.ScriptableApplication;
import com.dhemery.victor.os.Shell;

public class SimulatorApplication extends ScriptableApplication {
    public SimulatorApplication(Shell shell) {
        super(shell, "iPhone Simulator", "iOS Simulator");
    }
}
