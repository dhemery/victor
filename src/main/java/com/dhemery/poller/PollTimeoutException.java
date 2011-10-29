package com.dhemery.poller;

@SuppressWarnings("serial")
public class PollTimeoutException extends Exception {
	PollTimeoutException(String message) {
		super(message);
	}
}
