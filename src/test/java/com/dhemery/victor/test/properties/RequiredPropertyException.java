package com.dhemery.victor.test.properties;

@SuppressWarnings("serial")
public class RequiredPropertyException extends RuntimeException {

	public RequiredPropertyException(String missingPropertyName) {
		super(String.format("Please define property: %s", missingPropertyName));
	}

}
