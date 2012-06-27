package com.dhemery.victor.device.server;

import com.dhemery.victor.device.local.SimulatorApplication;
import com.dhemery.victor.device.remote.TouchMenuItemMessage;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<TouchMenuItemMessage> {
    public TouchMenuItemHandler(SimulatorApplication simulator) {
        super(simulator, TouchMenuItemMessage.class);
    }

    @Override
    public void perform(SimulatorApplication simulator, TouchMenuItemMessage message) {
        simulator.touchMenuItem(message.menuName(), message.menuItemName());
    }
}
