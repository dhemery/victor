package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.view.IosViewAgent;
import com.dhemery.victor.frank.frankly.CheckAccessibility;
import com.dhemery.victor.frank.frankly.GetApplicationOrientation;
import com.dhemery.victor.frank.frankly.PerformViewOperation;
import com.dhemery.victor.frank.frankly.Ping;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
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
public class FrankAgent implements SelfDescribing, IosViewAgent {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String serverUrl;
	private final Gson gson;

	/**
	 * @param serverUrl The URL where the Frank server listens for requests.
	 */
	public FrankAgent(String serverUrl) {
		this.serverUrl = serverUrl;
		gson = new GsonBuilder()
		.registerTypeAdapter(ResultsResponse.class, new ResultsResponseParser())
		.disableHtmlEscaping()
		.create();
	}

	/**
	 * Determines whether accessibility is enabled in the phone or simulator
	 * where the Frank server is running.
	 * @return a response that describes whether accessibility is enabled.
	 * @throws java.io.IOException
	 */
	public AccessibilityCheckResponse accessibilityCheck() throws IOException {
		return send(new CheckAccessibility(), AccessibilityCheckResponse.class);
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

	/**
	 * Determines the current orientation (portrait or landscape)
	 * of the application in which the Frank server is running.
	 * @return a response that describes the application's orientation.
	 * @throws java.io.IOException
	 */
	public OrientationResponse orientation() throws IOException {
		return send(new GetApplicationOrientation(), OrientationResponse.class);
	}

	@Override
    public List<String> perform(By query, Operation operation) throws IOException {
		ResultsResponse response = send(new PerformViewOperation(query, operation), ResultsResponse.class);
        if(!response.succeeded) {
            throw new FrankViewOperationException(String.format("Error for query %s", query), operation, response);
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
