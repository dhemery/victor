package com.dhemery.victor.phone;

import java.io.IOException;

import com.dhemery.victor.PhoneDriver;

public class SimulatedPhoneDriver implements PhoneDriver {
	private final SimulatorDriver simulator;

	public SimulatedPhoneDriver(SimulatorDriver simulator) {
		this.simulator = simulator;
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
