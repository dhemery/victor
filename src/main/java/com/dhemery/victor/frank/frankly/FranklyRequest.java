package com.dhemery.victor.frank.frankly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A request to a Frank server.
 * @author Dale Emery
 *
 */
public class FranklyRequest {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String verb;
	private final FranklyRequestBody body;
	
	/**
	 * Creates a Frank GET request.
	 * @param verb the path portion of the URL to which to send the request.
	 */
	public FranklyRequest(String verb) {
		this(verb, new FranklyRequestBody());
	}

	/**
	 * Creates a Frank request.
	 * If the body writes bytes to the connection,
	 * the request is sent as a POST request.
	 * Otherwise it is sent as a GET request.
	 * @param verb the path portion of the URL to which to send the request.
	 * @param body the body of the request.
	 */
	public FranklyRequest(String verb, FranklyRequestBody body) {
		this.verb = verb;
		this.body = body;
	}

	public String verb() { return verb; }
	public FranklyRequestBody body() { return body; }

	/**
	 * Sends the request to the Frank server and returns the server's response.
	 * @param serverUrl The URL where the Frank server listens for requests.
	 * @return The Frank server's response to the request.
	 * @throws IOException
	 */
	public FranklyResponse sendTo(String serverUrl) throws IOException {
		log.debug("Sending: {}", this);
		URL url = urlFor(serverUrl, verb);
		HttpURLConnection connection = connectTo(url);
		try {
			body.writeTo(connection);
			return receiveResponseFrom(connection);
		} finally {
			connection.disconnect();			
		}
	}

	private HttpURLConnection connectTo(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(30000);
		connection.setDoOutput(true);
		connection.connect();
		return connection;
	}

	private FranklyResponse receiveResponseFrom(HttpURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = in.readLine()) != null) {
			builder.append(line);
		}
		in.close();
		FranklyResponse response = new FranklyResponse(connection.getResponseMessage(), builder.toString());
		log.debug("Response: {}", response);
		return response;
	}

	protected URL urlFor(String serverUrl, String verb) {
		try {
			return new URL(String.format("%s/%s", serverUrl, verb));
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return String.format("[verb:%s][body:%s]", verb(), body());
	}
}
