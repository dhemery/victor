package com.dhemery.victor.phone;

import java.io.IOException;

import com.dhemery.victor.PhoneDriver;

public class SimulatedPhone implements PhoneDriver {
	private final Simulator simulator;

	public SimulatedPhone(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void rotateLeft() {
		touchMenuItem("Hardware", "Rotate Left");
	}

	@Override
	public void rotateRight() {
		touchMenuItem("Hardware", "Rotate Right");
	}
	
	private void touchMenuItem(String menuName, String menuItemName) {
		try {
			simulator.touchMenuItem(menuName, menuItemName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void shutDown() throws IOException, InterruptedException {
		simulator.shutDown();
	}
}
