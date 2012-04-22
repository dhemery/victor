package com.dhemery.victor.agent;

import com.dhemery.victor.By;
import com.dhemery.victor.application.IosApplicationAgent;
import com.dhemery.victor.application.OrientationResponse;
import com.dhemery.victor.frankly.GetApplicationOrientation;
import com.dhemery.victor.frankly.PerformViewOperation;
import com.dhemery.victor.frankly.Ping;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
import com.dhemery.victor.view.IosViewAgent;
import com.dhemery.victor.view.Operation;
import com.dhemery.victor.view.OperationResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * An agent that interacts with an iOS application via a Frank server embedded into the application.
 * @author Dale Emery
 *
 */
public class FrankAgent implements SelfDescribing, IosViewAgent, IosApplicationAgent {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverUrl;
	private final Gson gson;

	/**
	 * @param serverUrl The URL where the Frank server listens for requests.
	 */
	public FrankAgent(String serverUrl) {
		this.serverUrl = serverUrl;
		gson = new GsonBuilder()
		.registerTypeAdapter(OperationResponse.class, new ResultsResponseParser())
		.disableHtmlEscaping()
		.create();
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(toString());
	}

	/**
	 * Sends a GET request to the Frank server.
	 * @return true if the Frank server responds to the request, otherwise false.
	 */
	public boolean isReady() {
		new Ping().sendTo(serverUrl);
		return true;
	}

	@Override
    public OrientationResponse orientation() {
        try {
            return send(new GetApplicationOrientation(), OrientationResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
    public List<String> perform(By query, Operation operation) {
        OperationResponse response = null;
        try {
            response = send(new PerformViewOperation(query, operation), OperationResponse.class);
        } catch (IOException cause) {
            throw new OperationIOException(operation, cause);
        }
        if(!response.succeeded) {
            throw new OperationException(String.format("Error for query %s", query), operation, response);
        }
        return response.results;
	}

	private <T> T send(HttpRequest request, Class<T> resultsClass) throws IOException {
		HttpResponse response = request.sendTo(serverUrl);
		T results = gson.fromJson(response.body(), resultsClass);
		log.trace("Results from {} ==> {}", request, results);
		return results;
	}

	@Override
	public String toString() {
		return String.format("Frank client (%s)", serverUrl);
	}
}
