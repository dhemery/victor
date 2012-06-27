package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.SimulatorApplication;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
    public CloseSimulatorHandler(SimulatorApplication simulator) {
        super(simulator, Void.class);
    }

    @Override
    public void perform(SimulatorApplication simulator, Void ignored) {
    }
}
