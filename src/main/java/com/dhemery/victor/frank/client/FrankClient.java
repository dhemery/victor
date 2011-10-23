package com.dhemery.victor.frank.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Poll;
import com.dhemery.victor.elements.Locator;
import com.dhemery.victor.frank.json.ResultsResponseParser;
import com.dhemery.victor.http.Request;
import com.dhemery.victor.http.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FrankClient  {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverUrl;
	private final Gson gson;

	public FrankClient(String serverUrl) {
		this.serverUrl = serverUrl;
		gson = new GsonBuilder()
						.registerTypeAdapter(ResultsResponse.class, new ResultsResponseParser())
						.disableHtmlEscaping()
						.create();
	}

	public ResultsResponse map(Locator locator, String property) throws IOException {
		MapRequest request = new MapRequest(locator.toString(), property);
		Response response = request.sendTo(serverUrl);
		ResultsResponse results = gson.fromJson(response.body(), ResultsResponse.class);
		log.debug("Results from {} ==> {}", request, results);
		return results;
	}
	
	public OrientationResponse orientation() throws IOException {
		Request request = new OrientationRequest();
		Response response = request.sendTo(serverUrl);
		return gson.fromJson(response.body(), OrientationResponse.class);
	}

	public void waitUntilReady(Poll poll) {
		poll.until(new FrankServerAcknowledgesPing(serverUrl));
	}
}
