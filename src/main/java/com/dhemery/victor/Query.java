package com.dhemery.victor;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * <p>
 * A Query identifies views in an iOS application.
 * A query consists of two parts:
 * a selector engine name and a selector.
 * The selector engine name identifies which selector engine will perform the query.
 * The selector describes to the selector engine which views to select.
 * </p>
 * <p>The format of the selector depends on which selector engine performs the query.</p>
 * 
 * @author Dale Emery
 */
public class Query implements SelfDescribing {
	private final String selectorEngine;
	private final String selector;

	public Query(String selectorEngineName, String selector) {
		this.selectorEngine = selectorEngineName;
		this.selector = selector;
	}

	public String selector() { return selector; }
	public String selectorEngine() { return selectorEngine; }

	public String toString() {
		return String.format("query:[selectorEngine:%s][selector:%s]", selectorEngine(), selector());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}
}
