package com.dhemery.victor.symbiote;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.driver.Poll;
import com.dhemery.victor.elements.Element;
import com.google.gson.Gson;

public class SymbioteDriver implements ApplicationDriver {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Poll poll;
	private final String serverUrl;

	public SymbioteDriver(String serverUrl, Poll poll) {
		this.serverUrl = serverUrl;
		this.poll = poll;
	}

	private List<String> each(String locator, String property) {
		try {
			MapRequest request = new MapRequest(locator, property);
			Response response = request.sendTo(serverUrl);
			MapResponse mapResponse = new Gson().fromJson(response.body(), MapResponse.class);
			List<String> values = mapResponse.results();
			log.debug("[{}].{}: {}", new Object[] { locator, property, values} );
			return values;
		} catch (IOException e) {
			log.debug("Threw exception", e);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isPresent(Element element) {
		List<String> matches = each(element.locator(), "accessibilityLabel");
		return matches.size() == 1;
	}

	@Override
	public boolean isVisible(Element element) {
		List<String> matches = each(element.locator(), "isHidden");
		if(matches.size() != 1) return false;
		boolean isHidden = Boolean.parseBoolean(matches.get(0));
		return !isHidden;
	}

	@Override
	public void touch(Element element) {
		each(element.locator(), "touch");
	}

	public void waitUntilReady() {
		poll.until(new ServerAcknowledgesPing(serverUrl));
	}

	@Override
	public Poll poll() {
		return poll;
	}
}
