package com.dhemery.victor.frank.drivers;

import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.Query;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.application.ApplicationAssertion;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.OrientationResponse;

/**
 * An application driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class FrankApplicationDriver implements ApplicationDriver {
	private final FrankClient frank;
	private final Poll poll;
	private final String defaultSelectorEngine;

	/**
	 * @param frank a Frank client that can interact with this application.
	 * @param poll a poll to use to create view drivers and to perform verifications.
	 */
	public FrankApplicationDriver(FrankClient frank, String defaultSelectorEngine, Poll poll) {
		this.frank = frank;
		this.defaultSelectorEngine = defaultSelectorEngine;
		this.poll = poll;
	}

	@Override
	public boolean hasOrientation(Orientation testOrientation) {
		return orientation().equals(testOrientation);
	}

	@Override
	public Orientation orientation() {
		try {
			OrientationResponse response = frank.orientation();
			String orientationName = response.orientation().toUpperCase();
			return Orientation.valueOf(orientationName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ApplicationAssertion verify() {
		return new ApplicationAssertion(this, poll);
	}

	@Override
	public ViewDriver view(String selector) {
		return view(defaultSelectorEngine, selector);
	}

	@Override
	public ViewDriver view(String selectorEngine, String selector) {
		return new FrankViewDriver(frank, new Query(selectorEngine, selector), poll);
	}

	@Override
	public void waitUntilReady() throws PollTimeoutException {
		frank.waitForServer(poll);
	}
}
