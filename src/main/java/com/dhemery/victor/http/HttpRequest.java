package com.dhemery.victor.http;

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
	 */
	public HttpResponse sendTo(String serverUrl) {
		URL url = urlFor(serverUrl, verb);
		log.debug("Sending: {} {}", url.toString(), this);
		HttpConnection connection = new HttpConnection(url);
		return connection.send(body);
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
