package com.dhemery.victor.frank;

import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.application.ApplicationAssertion;

/**
 * A driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class FrankApplicationDriver implements ApplicationDriver {
	private final FrankClient frankClient;
	private final Poll poll;

	/**
	 * @param frankClient a client connected to the Frank server where the application is running.
	 * @param poll a poll to use to create view drivers and to perform verifications.
	 */
	public FrankApplicationDriver(FrankClient frankClient, Poll poll) {
		this.frankClient = frankClient;
		this.poll = poll;
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
	public ViewDriver view(String query) {
		return new FrankViewDriver(frankClient, query, poll);
	}

	@Override
	public ViewDriver view(String type, String name) {
		return view(String.format("%s marked:'%s'", type, name));
	}

	@Override
	public ApplicationAssertion verify() {
		return new ApplicationAssertion(this, poll);
	}

	@Override
	public boolean hasOrientation(Orientation testOrientation) {
		return orientation().equals(testOrientation);
	}
}
