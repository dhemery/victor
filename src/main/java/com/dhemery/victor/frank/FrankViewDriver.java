package com.dhemery.victor.frank;

import java.io.IOException;
import java.util.List;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.views.ViewAssertion;
import com.dhemery.victor.views.IsPresent;
import com.dhemery.victor.views.IsVisible;
import com.dhemery.victor.views.PolledViewCommands;

/**
 * A view driver that interacts with a view through a Frank server.
 * @author Dale Emery
 *
 */
public class FrankViewDriver implements ViewDriver {
	private final FrankClient frankClient;
	private final Poll poll;
	private final String query;

	/**
	 * @param frankClient a client connected to a frank server.
	 * @param query a query that selects the views driven by this driver.
	 * @param poll polls relevant conditions during the various {@link #when} methods.
	 */
	public FrankViewDriver(FrankClient frankClient, String query, Poll poll) {
		this.frankClient = frankClient;
		this.query = query;
		this.poll = poll;
	}

	@Override
	public void flash() throws IOException {
		invokeMethod("flash");
	}

	public ResultsResponse invokeMethod(String method) throws IOException {
		return frankClient.map(query, method);
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
			response = invokeMethod("accessibilityLabel");
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
			response = invokeMethod("isHidden");
		} catch (IOException e) {
			return false;
		}
		if(!response.succeeded()) return false;
		List<String> matches = response.results();
		if(matches.size() != 1) return false;
		boolean isHidden = Boolean.parseBoolean(matches.get(0));
		return !isHidden;
	}

	@Override
	public String query() {
		return query;
	}

	@Override
	public void touch() throws IOException {
		invokeMethod("touch");
	}

	@Override
	public ViewAssertion verify() {
		return new ViewAssertion(this, poll);
	}

	@Override
	public PolledViewCommands when(Condition condition) {
		return new PolledViewCommands(this, condition, poll);
	}

	@Override
	public PolledViewCommands whenPresent() {
		return when(new IsPresent(this));
	}

	@Override
	public PolledViewCommands whenVisible() {
		return when(new IsVisible(this));
	}
}
