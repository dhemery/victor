package com.dhemery.poller;

/**
 * A no-op {@link Command}.
 * Useful for polling until a condition is met,
 * without taking action during each polling cycle.
 * 
 * @author Dale Emery
 *
 */
public class Waiting implements Command {
	/**
	 * Does nothing.
	 */
	@Override
	public void execute(){}

	/**
	 * @return an indication that this poll is waiting, rather than taking action.
	 */
	@Override
	public String describe() {
		return "Waiting";
	}
}
