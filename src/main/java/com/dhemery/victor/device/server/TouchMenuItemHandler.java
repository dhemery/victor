package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.VictorSimulatorAgent;
import com.dhemery.victor.device.remote.TouchMenuItemMessage;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<TouchMenuItemMessage> {
    public TouchMenuItemHandler(VictorSimulatorAgent simulator) {
        super(simulator, TouchMenuItemMessage.class);
    }

    @Override
    public void perform(VictorSimulatorAgent simulator, TouchMenuItemMessage message) {
        simulator.touchMenuItem(message.menuName(), message.menuItemName());
    }
}
