package com.dhemery.victor.remote;

import java.io.IOException;
import java.util.List;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.application.server.ApplicationServer;
import com.dhemery.victor.application.server.Operation;
import com.dhemery.victor.application.server.ResultsResponse;
import com.dhemery.victor.view.IsPresent;
import com.dhemery.victor.view.IsVisible;
import com.dhemery.victor.view.ViewAssertion;

/**
 * A view driver that interacts with a view through an application server.
 * @author Dale Emery
 *
 */
public class RemoteViewDriver implements ViewDriver {
	private final ApplicationServer server;
	private final Poll poll;
	private final String query;

	/**
	 * @param server an application server that can interact with this view.
	 * @param query a query that selects the views driven by this driver.
	 * @param poll polls relevant conditions during the various {@link #when} methods.
	 */
	public RemoteViewDriver(ApplicationServer server, String query, Poll poll) {
		this.server = server;
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
		ResultsResponse response;
		try {
			response = call("accessibilityLabel");
		} catch (IOException e) {
			return false;
		}
		if(!response.succeeded()) return false;
		return response.results().size() == 1;
	}

	@Override
	public boolean isVisible() {
		ResultsResponse response;
		try {
			response = property("isHidden");
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
		return server.perform(query, operation);
	}

	private ResultsResponse property(String name) throws IOException {
		return perform(new Operation(name));
	}

	@Override
	public String query() {
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
	public ViewDriver when(Condition condition) throws PollTimeoutException {
		poll.until(condition);
		return this;
	}

	@Override
	public ViewDriver whenPresent() throws PollTimeoutException {
		when(new IsPresent(this));
		return this;
	}

	@Override
	public ViewDriver whenVisible() throws PollTimeoutException {
		when(new IsVisible(this));
		return this;
	}
}
