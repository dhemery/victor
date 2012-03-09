package com.dhemery.victor;

public class ShelleySelector extends ViewSelector {
	private static final String SELECTOR_ENGINE = "shelley_compat";

	public ShelleySelector(String selector) {
		super(SELECTOR_ENGINE, selector);
	}
}
