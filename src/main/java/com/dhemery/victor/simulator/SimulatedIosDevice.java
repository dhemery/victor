package com.dhemery.victor.simulator;

import com.dhemery.victor.IosDevice;

/**
 * A phone driver that interacts with a "phone" through a simulator and a Frank server.
 * @author Dale Emery
 *
 */
public class SimulatedIosDevice implements IosDevice {
	private final Simulator simulator;

	public SimulatedIosDevice(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void rotateLeft() {
		simulator.touchMenuItem("Hardware", "Rotate Left");
	}

	@Override
	public void rotateRight() {
		simulator.touchMenuItem("Hardware", "Rotate Right");
	}
	
	@Override
	public void saveScreenShot() {
		simulator.touchMenuItem("File", "Save Screen Shot");
	}
}
