package com.dhemery.victor.frank.elements;

import java.io.IOException;
import java.util.List;

import com.dhemery.poller.Poll;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ElementAssertion;
import com.dhemery.victor.elements.ElementCommands;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.elements.PolledElementCommands;
import com.dhemery.victor.elements.conditions.IsPresent;
import com.dhemery.victor.elements.conditions.IsVisible;
import com.dhemery.victor.frank.client.FrankClient;
import com.dhemery.victor.frank.client.ResultsResponse;

public class FrankElement implements Element {
	private final Locator locator;
	private final FrankClient frankClient;
	private final Poll poll;

	public FrankElement(FrankClient frankClient, Locator locator, Poll poll) {
		this.frankClient = frankClient;
		this.locator = locator;
		this.poll = poll;
	}

	@Override
	public boolean isPresent() {
		ResultsResponse response;
		try {
			response = frankClient.map(locator, "accessibilityLabel");
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
			response = frankClient.map(locator, "isHidden");
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
			frankClient.map(locator, "flash");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void touch() {
		try {
			frankClient.map(locator, "touch");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Locator locator() {
		return locator;
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
