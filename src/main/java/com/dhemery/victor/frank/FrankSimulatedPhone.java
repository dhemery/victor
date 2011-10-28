package com.dhemery.victor.frank;

import java.io.IOException;

import com.dhemery.victor.Phone;
import com.dhemery.victor.simulator.Simulator;

public class FrankSimulatedPhone implements Phone {
	private final Simulator simulator;

	public FrankSimulatedPhone(Simulator simulator) {
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
