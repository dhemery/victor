package com.dhemery.poller;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Clock} that delegates to system functions.
 * @author Dale Emery
 *
 */
public class SystemClock implements Clock {
	/**
	 * @return the current time (in milliseconds) as reported by the system.
	 */
	@Override
	public long now() {
		return System.currentTimeMillis();
	}

	/**
	 * Pause this thread for the duration.
	 */
	@Override
	public void sleep(long durationInMilliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(durationInMilliseconds);
		} catch (InterruptedException e) {
		}
	}
}
