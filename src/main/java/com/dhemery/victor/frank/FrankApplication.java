package com.dhemery.victor.frank;

import java.io.IOException;

import com.dhemery.poller.Poll;
import com.dhemery.victor.Application;
import com.dhemery.victor.Element;
import com.dhemery.victor.application.ApplicationAssertion;
import com.dhemery.victor.frank.client.FrankClient;
import com.dhemery.victor.frank.client.OrientationResponse;

public class FrankApplication implements Application {
	private final FrankClient frankClient;
	private final Poll poll;

	public FrankApplication(FrankClient frankClient, Poll poll) {
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
	public Element element(String query) {
		return new FrankElement(frankClient, query, poll);
	}

	@Override
	public Element element(String type, String name) {
		return element(String.format("%s marked:'%s'", type, name));
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
