package com.dhemery.victor.phone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.frank.FrankDriver;

public class Phone {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

	private final Logger log = LoggerFactory.getLogger(getClass());
	private final FrankDriver frank;

	public Phone(FrankDriver frank) {
		this.frank = frank;
	}

	public Orientation orientation() {
		String orientationName = frank.orientation().orientation();
		Orientation orientation = Orientation.UNKNOWN;
		try {
			orientation = Orientation.valueOf(orientationName.toUpperCase());
		} catch(IllegalArgumentException e) {
			log.debug("Frank returned orientation name {}", orientationName);
		}
		log.debug("Orientation is {}", orientation);
		return orientation;
	}
}
