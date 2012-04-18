package com.dhemery.victor.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnection {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final URL url;

	public HttpConnection(URL url) {
		this.url = url;
	}

	private HttpURLConnection connection() {
		HttpURLConnection connection = openHttpConnection();
		connection.setReadTimeout(30000);
		connection.setDoOutput(true);
		connectTo(connection);
		return connection;
	}
	
	private void connectTo(URLConnection connection) {
		try {
			connection.connect();
		} catch (IOException cause) {
			throw new HttpException(String.format("Exception while connecting to %s", connection.getURL()), cause);
		}
	}

	private void disconnectFrom(HttpURLConnection connection) {
		connection.disconnect();
	}

	private HttpURLConnection openHttpConnection() {
		try {
			return (HttpURLConnection) url.openConnection();
		} catch (IOException cause) {
			throw new HttpException(String.format("Exception opening connection to %s", url), cause);
		}
	}

	private HttpResponse responseFrom(HttpURLConnection connection) {
		HttpResponse response = new HttpResponse(responseMessageFrom(connection), responseBodyFrom(connection));
		log.trace("Response: {}", response);
		return response;
	}

	private String responseBodyFrom(HttpURLConnection connection) {
		return new HttpResponseBodyReader(connection).read();
	}

	private String responseMessageFrom(HttpURLConnection connection) {
		try {
			return connection.getResponseMessage();
		} catch (IOException cause) {
			throw new HttpException(String.format("Exception while reading response from %s", connection.getURL()), cause);
		}
	}

	public HttpResponse send(HttpRequestBody body) {
		HttpURLConnection connection = connection();
		body.writeTo(connection);
		HttpResponse response = responseFrom(connection);
		disconnectFrom(connection);	
		return response;
	}
}
