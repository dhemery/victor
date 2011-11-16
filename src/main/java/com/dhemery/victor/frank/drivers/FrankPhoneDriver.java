package com.dhemery.victor.frank.drivers;

import java.io.IOException;

import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.simulator.Simulator;

public class FrankPhoneDriver implements PhoneDriver {
	private final Simulator simulator;
	private final FrankClient frank;

	public FrankPhoneDriver(Simulator simulator, FrankClient frank) {
		this.simulator = simulator;
		this.frank = frank;
	}

	@Override
	public boolean isEnabledForAccessibility() throws IOException {
		String accessibilityEnabled = frank.accessibilityCheck().accessibilityEnabled();
		return Boolean.parseBoolean(accessibilityEnabled);
	}

	@Override
	public void rotateLeft() throws IOException, InterruptedException {
		simulator.touchMenuItem("Hardware", "Rotate Left");
	}

	@Override
	public void rotateRight() throws IOException, InterruptedException {
		simulator.touchMenuItem("Hardware", "Rotate Right");
	}
	
	@Override
	public void shutDown() throws IOException, InterruptedException {
		simulator.shutDown();
	}
}
