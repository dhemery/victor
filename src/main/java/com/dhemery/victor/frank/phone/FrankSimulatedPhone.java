package com.dhemery.victor.frank.phone;

import java.io.IOException;

import com.dhemery.victor.frank.client.FrankClient;
import com.dhemery.victor.frank.client.OrientationResponse;
import com.dhemery.victor.phone.Phone;
import com.dhemery.victor.simulator.Simulator;

public class FrankSimulatedPhone implements Phone {
	private final Simulator simulator;
	private final FrankClient frankClient;

	public FrankSimulatedPhone(Simulator simulator, FrankClient frankClient) {
		this.simulator = simulator;
		this.frankClient = frankClient;
	}

	@Override
	public Orientation orientation() {
		try {
			OrientationResponse response = frankClient.orientation();
			String orientationName = response.orientation().toUpperCase();
			return Orientation.valueOf(orientationName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
}
