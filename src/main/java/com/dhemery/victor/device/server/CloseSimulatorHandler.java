package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.VictorSimulatorAgent;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
    public CloseSimulatorHandler(VictorSimulatorAgent simulator) {
        super(simulator, Void.class);
    }

    @Override
    public void perform(VictorSimulatorAgent simulator, Void ignored) {
//		simulator.shutDown();
    }
}
