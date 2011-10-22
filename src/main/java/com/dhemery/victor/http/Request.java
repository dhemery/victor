package com.dhemery.victor.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Request {
	private final String verb;
	private final RequestBody body;
	
	public Request(String verb) {
		this(verb, new RequestBody());
	}

	public Request(String verb, RequestBody body) {
		this.verb = verb;
		this.body = body;
	}

	public Response sendTo(String serverUrl) throws IOException {
		URL url = urlFor(serverUrl, verb);
		HttpURLConnection connection = connectTo(url);
		try {
			body.writeTo(connection);
			Response response = receiveResponseFrom(connection);
			return response;
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

	private Response receiveResponseFrom(HttpURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		StringBuilder builder = new StringBuilder();
		while((line = in.readLine()) != null) {
			builder.append(line);
		}
		in.close();
		Response response = new Response(connection.getResponseMessage(), builder.toString());
		return response;
	}

	protected URL urlFor(String serverUrl, String verb) {
		try {
			return new URL(String.format("%s/%s", serverUrl, verb));
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
