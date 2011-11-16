package com.dhemery.victor.frank;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.poller.Condition;
import com.dhemery.poller.Poll;
import com.dhemery.poller.PollTimeoutException;
import com.dhemery.victor.Query;
import com.dhemery.victor.frank.frankly.AccessibilityCheckFranklyRequest;
import com.dhemery.victor.frank.frankly.FranklyResponse;
import com.dhemery.victor.frank.frankly.MapFranklyRequest;
import com.dhemery.victor.frank.frankly.OrientationFranklyRequest;
import com.dhemery.victor.frank.frankly.PingFranklyRequest;
import com.dhemery.victor.frank.frankly.FranklyRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A client that interacts with an iOS application via a Frank server embedded into the application.
 * @author Dale Emery
 *
 */
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

	public AccessibilityCheckResponse accessibilityCheck() throws IOException {
		FranklyRequest request = new AccessibilityCheckFranklyRequest();
		FranklyResponse response = request.sendTo(serverUrl);
		return gson.fromJson(response.body(), AccessibilityCheckResponse.class);
	}
	
	public OrientationResponse orientation() throws IOException {
		FranklyRequest request = new OrientationFranklyRequest();
		FranklyResponse response = request.sendTo(serverUrl);
		return gson.fromJson(response.body(), OrientationResponse.class);
	}

	public ResultsResponse perform(Query query, Operation operation) throws IOException {
		MapFranklyRequest request = new MapFranklyRequest(query, operation);
		FranklyResponse response = request.sendTo(serverUrl);
		ResultsResponse results = gson.fromJson(response.body(), ResultsResponse.class);
		log.debug("Results from {} ==> {}", request, results);
		return results;
	}

	private boolean ping() {
			try {
				new PingFranklyRequest().sendTo(serverUrl);
				return true;
			} catch (IOException e) {
				return false;
			}
	}

	public FrankClient waitForServer(Poll poll) throws PollTimeoutException {
		poll.until(new Condition() {
			@Override
			public String describe() {
				return "Frank server responds to ping";
			}

			@Override
			public boolean isSatisfied() {
				return ping();
			}
		});
		return this;
	}
}
