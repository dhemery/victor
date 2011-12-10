package com.dhemery.victor.view;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ViewDriver;

/**
 * <p>A driver that polls a {@code view} to determine whether certain conditions are true.
 * @author Dale Emery
 */
public class PolledViewConditions {
	private final ViewDriver view;
	private final Poll poll;

	/**
	 * @param view the view whose conditions to check.
	 * @param poll the {@link Poll} to use to poll the view.
	 */
	public PolledViewConditions(ViewDriver view, Poll poll) {
		this.view = view;
		this.poll = poll;
	}

	/**
	 * Polls until the view is not present.
	 * @throws PollTimeoutException if the poll times out while the view is present.
	 */
	public ViewDriver isNotPresent() throws PollTimeoutException {
		return when(new ViewMatcherCondition(view, new Not(new Present())));
	}

	/**
	 * Polls until the view is not visible.
	 * @throws PollTimeoutException if the poll times out while the view is visible.
	 */
	public ViewDriver isNotVisible() throws PollTimeoutException {
		return when(new ViewMatcherCondition(view, new Not(new Visible())));
	}
	
	/**
	 * Polls until the view is present.
	 * @throws PollTimeoutException if the poll times out before the view becomes present.
	 */
	public ViewDriver isPresent() throws PollTimeoutException {
		return when(new ViewMatcherCondition(view, new Present()));
	}

	/**
	 * Polls until the view until is visible.
	 * @throws PollTimeoutException if the poll times out before the view becomes visible.
	 */
	public ViewDriver isVisible() throws PollTimeoutException {
		return when(new ViewMatcherCondition(view, new Visible()));
	}

	private ViewDriver when(Condition condition) throws PollTimeoutException {
		poll.until(condition);
		return view;
	}
}
