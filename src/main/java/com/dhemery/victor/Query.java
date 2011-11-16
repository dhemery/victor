package com.dhemery.victor;

public class Query {
	private final String selectorEngine;
	private final String selector;

	public Query(String selectorEngine, String selector) {
		this.selectorEngine = selectorEngine;
		this.selector = selector;
	}

	public String selector() { return selector; }
	public String selectorEngine() { return selectorEngine; }

	public String toString() {
		return String.format("query:[engine:%s][selector:%s]", selectorEngine(), selector());
	}
}
