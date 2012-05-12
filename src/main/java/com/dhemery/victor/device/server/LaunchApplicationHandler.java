package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.VictorSimulatorAgent;
import com.dhemery.victor.device.remote.LaunchApplicationMessage;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<LaunchApplicationMessage> {
    public LaunchApplicationHandler(VictorSimulatorAgent simulator) {
        super(simulator, LaunchApplicationMessage.class);
    }

    @Override
    public void perform(VictorSimulatorAgent simulator, LaunchApplicationMessage message) {
//		simulator.launch();
    }
}
