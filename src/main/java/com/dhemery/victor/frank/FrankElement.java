package com.dhemery.victor.frank;

import java.io.IOException;
import java.util.List;

import com.dhemery.poller.Poll;
import com.dhemery.victor.Element;
import com.dhemery.victor.elements.ElementAssertion;
import com.dhemery.victor.elements.ElementCommands;
import com.dhemery.victor.elements.IsPresent;
import com.dhemery.victor.elements.IsVisible;
import com.dhemery.victor.elements.PolledElementCommands;
import com.dhemery.victor.frank.client.FrankClient;
import com.dhemery.victor.frank.client.ResultsResponse;

public class FrankElement implements Element {
	private final FrankClient frankClient;
	private final Poll poll;
	private final String query;

	public FrankElement(FrankClient frankClient, String query, Poll poll) {
		this.frankClient = frankClient;
		this.query = query;
		this.poll = poll;
	}

	@Override
	public boolean isPresent() {
		ResultsResponse response;
		try {
			response = frankClient.map(query, "accessibilityLabel");
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
			response = frankClient.map(query, "isHidden");
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
	public boolean isNotPresent() {
		return !isPresent();
	}

	@Override
	public boolean isNotVisible() {
		return !isVisible();
	}

	@Override
	public void flash() {
		try {
			frankClient.map(query, "flash");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void touch() {
		try {
			frankClient.map(query, "touch");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String query() {
		return query;
	}

	public ElementCommands whenPresent() {
		return new PolledElementCommands(poll, this, new IsPresent(this));
	}

	public ElementCommands whenVisible() {
		return new PolledElementCommands(poll, this, new IsVisible(this));
	}

	public ElementAssertion verify() {
		return new ElementAssertion(this, poll);
	}
}
