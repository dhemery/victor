package com.dhemery.victor.frank;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FrankClient  {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverUrl;
	private final Gson gson;
	private final Poll poll;

	public FrankClient(String serverUrl, Poll poll) {
		this.serverUrl = serverUrl;
		this.poll = poll;
		gson = new GsonBuilder()
						.registerTypeAdapter(ResultsResponse.class, new ResultsResponseParser())
						.disableHtmlEscaping()
						.create();
	}

	public ResultsResponse map(String query, String property) throws IOException {
		MapRequest request = new MapRequest(query, property);
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

	public void waitUntilReady() throws PollTimeoutException {
		poll.until(new FrankServerAcknowledgesPing(serverUrl));
	}
}
