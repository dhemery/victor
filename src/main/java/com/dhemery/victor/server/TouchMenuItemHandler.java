package com.dhemery.victor.server;

import java.io.IOException;

import com.dhemery.victor.simulator.RemoteTouchMenuItemCommandBody;
import com.dhemery.victor.simulator.VictorOwnedSimulator;

public class TouchMenuItemHandler extends SimulatorExchangeHandler<RemoteTouchMenuItemCommandBody> {
	public TouchMenuItemHandler(VictorOwnedSimulator simulator) {
		super(simulator, RemoteTouchMenuItemCommandBody.class);
	}

	@Override
	public void perform(VictorOwnedSimulator simulator, RemoteTouchMenuItemCommandBody body) throws IOException, InterruptedException {
		simulator.touchMenuItem(body.menuName, body.menuItemName);
	}
}
