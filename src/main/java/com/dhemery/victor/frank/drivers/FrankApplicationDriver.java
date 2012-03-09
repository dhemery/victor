package com.dhemery.victor.frank.drivers;

import java.io.IOException;

import org.hamcrest.Description;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ViewSelector;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.OrientationResponse;

/**
 * An application driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class FrankApplicationDriver implements ApplicationDriver {
	private final FrankClient frank;

	/**
	 * @param frank a Frank client that can interact with this application.
	 */
	public FrankApplicationDriver(FrankClient frank) {
		this.frank = frank;
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
	public ViewDriver view(ViewSelector selector) {
		return new FrankViewDriver(frank, selector);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	@Override
	public String toString() {
		return "the application";
	}
}
