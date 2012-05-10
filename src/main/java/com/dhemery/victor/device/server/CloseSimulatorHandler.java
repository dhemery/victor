package com.dhemery.victor.device.server;

import com.dhemery.victor.device.VictorSimulatorAgent;

import java.io.IOException;

public class CloseSimulatorHandler extends SimulatorExchangeHandler<Void> {
    public CloseSimulatorHandler(VictorSimulatorAgent simulator) {
        super(simulator, Void.class);
    }

    @Override
    public void perform(VictorSimulatorAgent simulator, Void ignored) throws IOException, InterruptedException {
//		simulator.shutDown();
    }
}
