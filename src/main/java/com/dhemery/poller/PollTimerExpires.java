package com.dhemery.poller;

/**
 * A condition that is never satisfied.
 * This is useful to create a poll that repeatedly executes a command
 * until a duration passes.
 * 
 * @author Dale
 *
 */
public class PollTimerExpires extends Condition {
	private final long durationInMilliseconds;

	/**
	 * 
	 * @param durationInMilliseconds the duration of the poll (used only to describe this condition).
	 */
	public PollTimerExpires(long durationInMilliseconds) {
		this.durationInMilliseconds = durationInMilliseconds;		
	}

	/**
	 * @return false (this condition is never satisfied)
	 */
	@Override
	public boolean isSatisfied() {
		return false;
	}

	/**
	 * @return a description of the poll's duration.
	 */
	@Override
	public String describe() {
		return String.format("duration (%d) passes", durationInMilliseconds);
	}
}
