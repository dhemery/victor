package com.dhemery.victor.device.server;

import com.dhemery.victor.device.SimulatorAgent;
import com.dhemery.victor.device.remote.TouchMenuItemMessage;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<TouchMenuItemMessage> {
    public TouchMenuItemHandler(SimulatorAgent simulator) {
        super(simulator, TouchMenuItemMessage.class);
    }

    @Override
    public void perform(SimulatorAgent simulator, TouchMenuItemMessage message) {
        simulator.touchMenuItem(message.menuName(), message.menuItemName());
    }
}
