package com.dhemery.victor.frank;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.application.server.ApplicationServer;
import com.dhemery.victor.application.server.Operation;
import com.dhemery.victor.application.server.OrientationResponse;
import com.dhemery.victor.application.server.ResultsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A driver that interacts with an iOS application via a Frank server embedded into the application.
 * @author Dale Emery
 *
 */
public class FrankServer implements ApplicationServer  {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverUrl;
	private final Gson gson;

	public FrankServer(String serverUrl) {
		this.serverUrl = serverUrl;
		gson = new GsonBuilder()
						.registerTypeAdapter(ResultsResponse.class, new ResultsResponseParser())
						.disableHtmlEscaping()
						.create();
	}

	@Override
	public ResultsResponse perform(String query, Operation operation) throws IOException {
		MapRequest request = new MapRequest(query, operation);
		Response response = request.sendTo(serverUrl);
		ResultsResponse results = gson.fromJson(response.body(), ResultsResponse.class);
		log.debug("Results from {} ==> {}", request, results);
		return results;
	}
	
	@Override
	public OrientationResponse orientation() throws IOException {
		Request request = new OrientationRequest();
		Response response = request.sendTo(serverUrl);
		return gson.fromJson(response.body(), OrientationResponse.class);
	}

	@Override
	public boolean ping() {
			try {
				new PingRequest().sendTo(serverUrl);
				return true;
			} catch (IOException e) {
				return false;
			}
	}
}
