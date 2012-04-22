package com.dhemery.victor.frank;

import com.dhemery.victor.IosApplication;
import org.hamcrest.Description;

import java.io.IOException;

/**
 * An application driver that interacts with an application through a Frank server.
 * @author Dale Emery
 */
public class FrankIosApplication implements IosApplication {
	private final FrankAgent frank;

	/**
	 * @param frank a Frank client that can interact with this application.
	 */
	public FrankIosApplication(FrankAgent frank) {
		this.frank = frank;
	}

	@Override
	public Orientation orientation() {
		try {
			OrientationResponse response = frank.orientation();
			String orientationName = response.orientation.toUpperCase();
			return Orientation.valueOf(orientationName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
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
