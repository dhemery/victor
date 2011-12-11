package com.dhemery.victor.application;

import static com.dhemery.victor.matchers.MatcherCondition.subject;
import static com.dhemery.victor.application.OrientationMatcher.orientation;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.RequiredConditionException;
import com.dhemery.victor.ApplicationDriver;
import com.dhemery.victor.ApplicationDriver.Orientation;
/**
 * <p>
 * Checks conditions on an application.
 * If a condition is not true when checked, the check throws an exception.
 * </p>
 * 
 * @author Dale Emery
 */
public class ApplicationAssertion {
	private final ApplicationDriver application;
	private final Poll poll;

	/**
	 * @param application the application whose conditions to check.
	 * @param poll the poll to use for {@link #eventually()} checks.
	 */
	public ApplicationAssertion(ApplicationDriver application, Poll poll) {
		this.application = application;
		this.poll = poll;		
	}

	/**
	 * @return a driver that repeatedly polls the application for specified conditions.
	 */
	public PolledApplicationConditions eventually() {
		return new PolledApplicationConditions(application, poll);
	}

	/**
	 * @throws RequiredConditionException if the application does not have the required orientation.
	 */
	public void hasOrientation(Orientation orientation) throws RequiredConditionException {
		Condition condition = subject(application).has(orientation(orientation));
		if(!condition.isSatisfied()) {
			throw new RequiredConditionException(condition);
		}
	}
}
