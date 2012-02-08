package com.dhemery.victor.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A request to an HTTP server.
 * @author Dale Emery
 *
 */
public class HttpRequest {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final String verb;
	private final HttpRequestBody body;
	
	/**
	 * Creates an HTTP GET request.
	 * @param verb the path portion of the URL to which to send the request.
	 */
	public HttpRequest(String verb) {
		this(verb, new HttpRequestBody());
	}

	/**
	 * Creates an HTTP request.
	 * If the body writes bytes to the connection,
	 * the request is sent as a POST request.
	 * Otherwise it is sent as a GET request.
	 * @param verb the path portion of the URL to which to send the request.
	 * @param body the body of the request.
	 */
	public HttpRequest(String verb, HttpRequestBody body) {
		this.verb = verb;
		this.body = body;
	}

	public String verb() { return verb; }
	public HttpRequestBody body() { return body; }

	/**
	 * Sends the request to the HTTP server and returns the server's response.
	 * @param serverUrl The URL where the HTTP server listens for requests.
	 * @return The HTTP server's response to the request.
	 * @throws IOException
	 */
	public HttpResponse sendTo(String serverUrl) throws IOException {
		URL url = urlFor(serverUrl, verb);
		log.debug("Sending: {} {}", url.toString(), this);
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

	private HttpResponse receiveResponseFrom(HttpURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = in.readLine()) != null) {
			builder.append(line);
		}
		in.close();
		HttpResponse response = new HttpResponse(connection.getResponseMessage(), builder.toString());
		log.debug("Response: {}", response);
		return response;
	}

	protected URL urlFor(String serverUrl, String verb) {
		String fullUrl = String.format("%s/%s", serverUrl, verb);
		try {
			return new URL(fullUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public String toString() {
		return String.format("[body:%s]", body());
	}
}
