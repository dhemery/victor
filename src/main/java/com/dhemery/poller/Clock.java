package com.dhemery.poller;

/**
 * A clock used by {@link Poll} to determine the current time and to sleep.
 * @author Dale Emery
 *
 */
public interface Clock {
	long now();
	void sleep(long intervalInMilliseconds);
}
