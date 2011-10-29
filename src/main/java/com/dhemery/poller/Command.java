package com.dhemery.poller;

/**
 * A command to be run repeatedly by the poller.
 * @author Dale Emery
 *
 */
public interface Command {
	/**
	 * @return a description of the command.
	 */
	String describe();
	/**
	 * Execute the command.
	 */
	void execute();
}