package com.dhemery.victor.remote;

import java.io.IOException;

import com.dhemery.victor.PhoneDriver;
import com.dhemery.victor.application.server.ApplicationServer;
import com.dhemery.victor.simulator.Simulator;

public class RemotePhoneDriver implements PhoneDriver {
	private final Simulator simulator;
	private final ApplicationServer server;

	public RemotePhoneDriver(Simulator simulator, ApplicationServer server) {
		this.simulator = simulator;
		this.server = server;
	}

	@Override
	public boolean isEnabledForAccessibility() throws IOException {
		String accessibilityEnabled = server.accessibilityCheck().accessibilityEnabled();
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
