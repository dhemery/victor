package com.dhemery.victor.remote;

import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.application.ApplicationAssertion;
import com.dhemery.victor.application.server.ApplicationServer;
import com.dhemery.victor.application.server.OrientationResponse;

/**
 * A driver that interacts with an application through an application server.
 * @author Dale Emery
 */
public class RemoteApplicationDriver implements ApplicationDriver {
	private final ApplicationServer server;
	private final Poll poll;

	/**
	 * @param server an application server that can interact with this application.
	 * @param poll a poll to use to create view drivers and to perform verifications.
	 */
	public RemoteApplicationDriver(ApplicationServer server, Poll poll) {
		this.server = server;
		this.poll = poll;
	}

	@Override
	public Orientation orientation() {
		try {
			OrientationResponse response = server.orientation();
			String orientationName = response.orientation().toUpperCase();
			return Orientation.valueOf(orientationName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ViewDriver view(String query) {
		return new RemoteViewDriver(server, query, poll);
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
