package com.dhemery.victor.frank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Poll;
import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.http.Response;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FrankDriver implements ApplicationDriver {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Poll poll;
	private final String serverUrl;

	public FrankDriver(String serverUrl, Poll poll) {
		this.serverUrl = serverUrl;
		this.poll = poll;
	}

	private List<String> each(Locator locator, String property) {
		MapRequest request = new MapRequest(locator.toString(), property);
		Response response;
		try {
			response = request.sendTo(serverUrl);
		} catch (IOException e) {
			log.debug("Threw exception", e);
			return Collections.emptyList();
		}
		List<String> matches = getMatches(response.body());
		log.debug("[{}] {}: {}", new Object[] { locator, property, matches} );
		return matches;
	}

	private List<String> getMatches(String stringBody) {
		List<String> matches;
		matches = new ArrayList<String>();
		JsonObject body = new JsonParser().parse(stringBody).getAsJsonObject();
		for(JsonElement element : body.get("results").getAsJsonArray()) {
			matches.add(element.getAsString());
		}
		return matches;
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
		poll.until(new FrankServerAcknowledgesPing(serverUrl));
	}

	@Override
	public Poll poll() {
		return poll;
	}
}
