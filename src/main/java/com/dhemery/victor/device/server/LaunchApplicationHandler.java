package com.dhemery.victor.device.server;

import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.device.remote.LaunchApplicationMessage;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<LaunchApplicationMessage> {
    public LaunchApplicationHandler(SimulatorAgent simulator) {
        super(simulator, LaunchApplicationMessage.class);
    }

    @Override
    public void perform(SimulatorAgent simulator, LaunchApplicationMessage message) {
//		simulator.launch();
    }
}
