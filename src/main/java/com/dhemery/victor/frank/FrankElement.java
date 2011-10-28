package com.dhemery.victor.frank;

import java.io.IOException;
import java.util.List;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.victor.ViewDriver;
import com.dhemery.victor.views.ElementAssertion;
import com.dhemery.victor.views.ElementCommands;
import com.dhemery.victor.views.IsPresent;
import com.dhemery.victor.views.IsVisible;
import com.dhemery.victor.views.PolledElementCommands;

public class FrankElement implements ViewDriver {
	private final FrankClient frankClient;
	private final Poll poll;
	private final String query;

	public FrankElement(FrankClient frankClient, String query, Poll poll) {
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
	public ElementAssertion verify() {
		return new ElementAssertion(this, poll);
	}

	@Override
	public ElementCommands when(Condition condition) {
		return new PolledElementCommands(this, condition, poll);
	}

	@Override
	public ElementCommands whenPresent() {
		return when(new IsPresent(this));
	}

	@Override
	public ElementCommands whenVisible() {
		return when(new IsVisible(this));
	}
}
