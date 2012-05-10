package com.dhemery.victor.device.server;

import com.dhemery.victor.device.VictorSimulatorAgent;
import com.dhemery.victor.device.remote.TouchMenuItemMessage;

import java.io.IOException;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<TouchMenuItemMessage> {
    public TouchMenuItemHandler(VictorSimulatorAgent simulator) {
        super(simulator, TouchMenuItemMessage.class);
    }

    @Override
    public void perform(VictorSimulatorAgent simulator, TouchMenuItemMessage message) throws IOException, InterruptedException {
        simulator.touchMenuItem(message.menuName, message.menuItemName);
    }
}
