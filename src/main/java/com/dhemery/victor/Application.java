package com.dhemery.victor;

import com.dhemery.victor.application.ApplicationAssertion;
import com.dhemery.victor.application.ApplicationConditions;

public interface Application extends ApplicationConditions {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

	public Element element(String query);
	public Element element(String viewType, String name);
	public Orientation orientation();
	public ApplicationAssertion verify();
}
