package com.dhemery.poller;

/**
 * Creates and initiates polls that poll for a given duration.
 * @author Dale Emery
 *
 */
public class For {
	private final long durationInMilliseconds;

	/**
	 * Prepares to create a poll that polls for the duration.
	 * @param durationInMilliseconds
	 */
	public For(long durationInMilliseconds) {
		this.durationInMilliseconds = durationInMilliseconds;
	}

	/**
	 * @param command the command to execute on each poll.
	 * @return a poll that polls the command for the duration.
	 */
	public Poll execute(Command command) {
		return new Poll(command, durationInMilliseconds);
	}

	/**
	 * 
	 * @return a poll that executes a {@link Waiting} command (a no-op command) for the duration.
	 */
	public Poll poll() {
		return execute(new Waiting());
	}
}
