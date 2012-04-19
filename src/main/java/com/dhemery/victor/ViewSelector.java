package com.dhemery.victor;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * <p>
 * A ViewQuery identifies views in an iOS application.
 * A view query consists of two parts:
 * a selector engine name and a selector.
 * The selector engine name identifies which selector engine will perform the query.
 * The selector describes to the selector engine which views to select.
 * </p>
 * <p>The format of the selector depends on which selector engine performs the query.</p>
 * 
 * @author Dale Emery
 */
public class ViewSelector implements SelfDescribing {
	private final String selectorEngine;
	private final String selector;

	public ViewSelector(String selectorEngineName, String selector) {
		this.selectorEngine = selectorEngineName;
		this.selector = selector;
	}

	public String selector() { return selector; }
	public String selectorEngine() { return selectorEngine; }

	@Override
    public String toString() {
		return String.format("%s:%s", selectorEngine(), selector());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}
}
