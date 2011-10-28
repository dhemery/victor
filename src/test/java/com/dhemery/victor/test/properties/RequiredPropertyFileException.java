package com.dhemery.victor.test.properties;

@SuppressWarnings("serial")
public class RequiredPropertyFileException extends RuntimeException {

	public RequiredPropertyFileException(String filename) {
		super(String.format("Please create a properties file named %s", filename));
	}
}
