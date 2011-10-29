package com.dhemery.victor.views;

import java.io.IOException;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ViewDriver;

/**
 * A view driver that polls the view for some condition before it executes each command.
 * @author Dale Emery
 */
public class PolledViewCommands {
	private final ViewDriver view;
	private final Condition ready;
	private final Poll poll;

	/**
	 * @param view a "bare" (unpolled) driver for the view.
	 * @param condition a condition that must be satisfied before the driver executes each command.
	 * @param poll the poll to use to wait for the condition.
	 */
	public PolledViewCommands(ViewDriver view, Condition condition, Poll poll) {
		this.view = view;
		this.ready = condition;
		this.poll = poll;
	}

	/**
	 * Waits until the view satisfies the polled condition, then touches the view.
	 * @throws IOException
	 * @throws PollTimeoutException
	 */
	public void flash() throws IOException, PollTimeoutException {
		poll.until(ready);
		view.flash();
	}

	/**
	 * Waits until the view satisfies the polled condition, then flashes the view.
	 * @throws IOException
	 * @throws PollTimeoutException
	 */
	public void touch() throws IOException, PollTimeoutException {
		poll.until(ready);
		view.touch();
	}
}
