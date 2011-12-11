package com.dhemery.victor.application;

import static com.dhemery.victor.application.OrientationMatcher.orientation;
import static com.dhemery.victor.matchers.MatcherCondition.subject;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;
/**
 * <p>A driver that polls an {@code Application} to determine whether certain conditions are true.
 * 
 * @author Dale Emery
 *
 */
public class PolledApplicationConditions {
	private final ApplicationDriver application;
	private final Poll poll;

	/**
	 * @param application the application to poll.
	 * @param poll the {@link Poll} to use to poll the application.
	 */
	public PolledApplicationConditions(ApplicationDriver application, Poll poll) {
		this.application = application;
		this.poll = poll;
	}

	/**
	 * <p>Polls until the application has the given orientation.</p>
	 * @throws PollTimeoutException if the poll times out before the application has the given orientation. 
	 */
	public void hasOrientation(Orientation orientation) throws PollTimeoutException {
		poll.until(subject(application).has(orientation(orientation)));
	}
}
