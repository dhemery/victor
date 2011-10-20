package com.dhemery.victor.driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.dhemery.poller.Condition;
import com.dhemery.poller.For;

public class VictorClient implements IOSApplicationDriver {
	private static final String SERVER_URL = "http://localhost:37265/";
	private URL serverURL = urlFor(SERVER_URL);
	private final String path;

	public VictorClient(String path) {
		this.path = path;
	}

	@Override
	public boolean elementExists(String locator) {
		Object[] matches;
		try {
			matches = franklyMap(locator, "accessibilityLabel");
		} catch (IOException e) {
			return false;
		}
		return matches.length > 0;
	}

	private Object[] franklyMap(String query, String methodName) throws IOException {
		Map<String,String> operationMap = new HashMap<String,String>();
		operationMap.put("method_name", methodName);
		Map<Object,Object> commandHash = new HashMap<Object,Object>();
		commandHash.put("query", query);
		commandHash.put("operation", operationMap);
		String response = postToUISpecServer("map", operationMap);
		System.out.println("Returned: " + response);
		return new Object[] { response } ;
	}

	private String postToUISpecServer(String verb, Map<String, String> commandHash) throws IOException {
		URL url = frankUrlFor(verb);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setRequestMethod("PUT");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write("");
			out.close();
			System.out.println("response: " + connection.getResponseMessage());
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder builder = new StringBuilder();
			while((line = in.readLine()) != null) {
				builder.append(line);
			}
			in.close();
			return builder.toString();
		} catch(Throwable e) {
			return "";
		} finally {
			connection.disconnect();			
		}
	}

	private URL frankUrlFor(String verb) {
		return urlFor(String.format("%s/%s", SERVER_URL, verb));
	}

	@Override
	public boolean elementIsVisible(String locator) {
		return false;
	}

	private HttpURLConnection openConnection() throws IOException {
		return (HttpURLConnection) serverURL.openConnection();
	}

	public String path() {
		return path;
	}

	private boolean ping() {
		try {
			HttpURLConnection connection = openConnection();
			try {
				connection.setRequestMethod("GET");
				connection.setDoOutput(true);
				connection.setReadTimeout(10000);
				connection.connect();
				return true;
			} catch(Throwable e) {
				return false;
			} finally {
				connection.disconnect();			
			}
		} catch (IOException e) {
			return false;
		}
	}

	private static URL urlFor(String spec) {
		URL url = null;
		try {
			url = new URL(spec);
		} catch (MalformedURLException e) {
		}
		return url;
	}

	public void waitForServer() {
		new For(20000).poll().until(applicationServerResponds());
	}

	private Condition applicationServerResponds() {
		return new Condition() {
			@Override
			public boolean isSatisfied() {
				return ping();
			}

			@Override
			public String describe() {
				return "application server responds";
			}
		};
	}
}
