package com.dhemery.victor.frank.drivers;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.Query;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.frank.FrankClient;
import com.dhemery.victor.frank.Operation;
import com.dhemery.victor.frank.ResultsResponse;
import com.dhemery.victor.view.Present;
import com.dhemery.victor.view.ViewAssertion;
import com.dhemery.victor.view.ViewMatcherCondition;
import com.dhemery.victor.view.Visible;

/**
 * A view driver that interacts with a view through a Frank server.
 * @author Dale Emery
 *
 */
public class FrankViewDriver implements ViewDriver {
	private final FrankClient frank;
	private final Poll poll;
	private final Query query;

	/**
	 * @param frank a Frank client that can interact with this view.
	 * @param query a query that identifies the views driven by this driver.
	 * @param poll polls relevant conditions during the various {@link #when} methods.
	 */
	public FrankViewDriver(FrankClient frank, Query query, Poll poll) {
		this.frank = frank;
		this.query = query;
		this.poll = poll;
	}

	private ResultsResponse call(String method, String...arguments) throws IOException {
		return perform(new Operation(method, arguments));
	}

	@Override
	public void flash() throws IOException {
		call("flash");
	}

	@Override
	public boolean isNotPresent() {
		return !isPresent();
	}
	
	@Override
	public boolean isNotVisible() {
		return !isVisible();
	}

	@Override
	public boolean isPresent() {
		String property = "accessibilityLabel";
		ResultsResponse response;
		try {
			response = call(property);
		} catch (IOException e) {
			return false;
		}
		if(!response.succeeded()) return false;
		List<String> values = response.results();
		return values.size() == 1;
	}

	@Override
	public boolean isVisible() {
		String property = "isHidden";
		ResultsResponse response;
		try {
			response = property(property);
		} catch (IOException e) {
			return false;
		}
		if(!response.succeeded()) return false;
		List<String> values = response.results();
		if(values.size() != 1) return false;
		boolean isHidden = Boolean.parseBoolean(values.get(0));
		return !isHidden;
	}

	private ResultsResponse perform(Operation operation) throws IOException {
		return frank.perform(query, operation);
	}

	private ResultsResponse property(String propertyName) throws IOException {
		return perform(new Operation(propertyName));
	}

	@Override
	public Query query() {
		return query;
	}

	@Override
	public void touch() throws IOException {
		call("touch");
	}

	@Override
	public ViewAssertion verify() {
		return new ViewAssertion(this, poll);
	}

	@Override
	public ViewDriver when(Matcher<ViewDriver> matcher) throws PollTimeoutException {
		Condition condition = new  ViewMatcherCondition(this, matcher);
		poll.until(condition);
		return this;
	}

	@Override
	public ViewDriver whenPresent() throws PollTimeoutException {
		when(new Present());
		return this;
	}

	@Override
	public ViewDriver whenVisible() throws PollTimeoutException {
		when(new Visible());
		return this;
	}

	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(query());
	}

}
