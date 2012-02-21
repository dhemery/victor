package com.dhemery.victor.simulator.local;


public class OSCommandException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OSCommandException(String command, Throwable cause) {
		super(String.format("Exception while executing command %s", command), cause);
	}
}
