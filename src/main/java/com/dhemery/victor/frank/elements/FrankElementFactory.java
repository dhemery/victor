package com.dhemery.victor.frank.elements;

import com.dhemery.poller.Poll;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.ElementFactory;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.frank.client.FrankClient;

public class FrankElementFactory implements ElementFactory {
	private final FrankClient frankClient;
	private final Poll poll;

	public FrankElementFactory(FrankClient frankClient, Poll poll) {
		this.frankClient = frankClient;
		this.poll = poll;
	}

	public Element element(Locator locator) {
		return new FrankElement(frankClient, locator, poll);
	}

	public Element element(String type, String name) {
		return element(new FrankLocator(type, name));
	}
}
