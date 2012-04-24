package com.dhemery.victor.device.local;


public class OSCommandException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OSCommandException(OSCommand command, Throwable cause) {
		super(String.format("Exception while executing command %s", command), cause);
	}
}
