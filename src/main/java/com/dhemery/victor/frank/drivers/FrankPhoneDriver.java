package com.dhemery.victor.frank.drivers;

import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.simulator.Simulator;

/**
 * A phone driver that interacts with a "phone" through a simulator and a Frank server.
 * @author Dale Emery
 *
 */
public class FrankPhoneDriver implements PhoneDriver {
	private final Simulator simulator;

	public FrankPhoneDriver(Simulator simulator) {
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
