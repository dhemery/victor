package com.dhemery.victor.device.server;

import com.dhemery.victor.device.SimulatorAgent;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
    public CloseSimulatorHandler(SimulatorAgent simulator) {
        super(simulator, Void.class);
    }

    @Override
    public void perform(SimulatorAgent simulator, Void ignored) {
//		simulator.shutDown();
    }
}
