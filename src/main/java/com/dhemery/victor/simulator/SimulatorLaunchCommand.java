package com.dhemery.victor.simulator;

import com.dhemery.victor.simulator.local.OSCommand;

import java.util.List;

public class SimulatorLaunchCommand {
    private String simulatorCommand() {
        return null;
    }

    private List<String> simulatorOptions() {
        return null;
    }

    private List<String> simulatorEnvironment() {
        return null;
    }

    public boolean isRunningHeadless() {
        return false;
    }

    public OSCommand launchCommand() {
        return new OSCommand(simulatorCommand(), simulatorOptions(), simulatorEnvironment());
    }


}
