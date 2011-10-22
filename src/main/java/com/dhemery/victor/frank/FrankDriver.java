package com.dhemery.victor.frank;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Poll;
import com.dhemery.victor.driver.ApplicationDriver;
import com.dhemery.victor.elements.Element;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.http.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FrankDriver implements ApplicationDriver {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Poll poll;
	private final String serverUrl;
	private final Gson gson;

	public FrankDriver(String serverUrl, Poll poll) {
		this.serverUrl = serverUrl;
		this.poll = poll;
		gson = new GsonBuilder().registerTypeAdapter(ResultsResponse.class, new ResultsResponseParser()).create();
	}

	private ResultsResponse each(Locator locator, String property) {
		MapRequest request = new MapRequest(locator.toString(), property);
		Response response;
		try {
			response = request.sendTo(serverUrl);
		} catch (IOException e) {
			log.debug("Threw exception", e);
			List<String> results = Collections.emptyList();
			return new ResultsResponse(false, results, "Exception", e.getMessage());
		}
		ResultsResponse results = gson.fromJson(response.body(), ResultsResponse.class);
		log.debug("[{}] {}: {}", new Object[] { locator, property, results.results()} );
		return results;
	}

	@Override
	public boolean isPresent(Element element) {
		List<String> matches = each(element.locator(), "accessibilityLabel").results();
		return matches.size() == 1;
	}

	@Override
	public boolean isVisible(Element element) {
		List<String> matches = each(element.locator(), "isHidden").results();
		if(matches.size() != 1) return false;
		boolean isHidden = Boolean.parseBoolean(matches.get(0));
		return !isHidden;
	}

	@Override
	public void touch(Element element) {
		each(element.locator(), "touch");
	}

	@Override
	public void flash(Element element) {
		each(element.locator(), "flash");
	}
	
	public void waitUntilReady() {
		poll.until(new FrankServerAcknowledgesPing(serverUrl));
	}

	@Override
	public Poll poll() {
		return poll;
	}
}
