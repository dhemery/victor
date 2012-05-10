package com.dhemery.victor.device.server;

import com.dhemery.victor.device.VictorSimulatorAgent;
import com.dhemery.victor.device.remote.LaunchApplicationMessage;

import java.io.IOException;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<LaunchApplicationMessage> {
    public LaunchApplicationHandler(VictorSimulatorAgent simulator) {
        super(simulator, LaunchApplicationMessage.class);
    }

    @Override
    public void perform(VictorSimulatorAgent simulator, LaunchApplicationMessage message) throws IOException {
//		simulator.launch();
    }
}
