package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.SimulatorApplication;
import com.dhemery.victor.device.remote.LaunchApplicationMessage;

public class LaunchApplicationHandler extends SimulatorExchangeHandler<LaunchApplicationMessage> {
    public LaunchApplicationHandler(SimulatorApplication simulator) {
        super(simulator, LaunchApplicationMessage.class);
    }

    @Override
    public void perform(SimulatorApplication simulator, LaunchApplicationMessage message) {
    }
}
