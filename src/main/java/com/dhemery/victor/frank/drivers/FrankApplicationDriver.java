package com.dhemery.victor.frank.drivers;

import java.io.IOException;

import org.hamcrest.Description;

import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.Query;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.OrientationResponse;

/**
 * An application driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class FrankApplicationDriver implements ApplicationDriver {
	private final FrankClient frank;
	private final String defaultSelectorEngine;

	/**
	 * @param frank a Frank client that can interact with this application.
	 */
	public FrankApplicationDriver(FrankClient frank, String defaultSelectorEngine) {
		this.frank = frank;
		this.defaultSelectorEngine = defaultSelectorEngine;
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
	public ViewDriver view(String selector) {
		return view(defaultSelectorEngine, selector);
	}

	@Override
	public ViewDriver view(String selectorEngine, String selector) {
		return new FrankViewDriver(frank, new Query(selectorEngine, selector));
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
