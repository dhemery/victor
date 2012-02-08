package com.dhemery.victor.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.simulator.VictorOwnedSimulator;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class SimulatorExchangeHandler<T> implements HttpHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final VictorOwnedSimulator simulator;
	private final Class<T> type;

	public SimulatorExchangeHandler(VictorOwnedSimulator simulator, Class<T> type) {
		this.simulator = simulator;
		this.type = type;
	}

	private String errorMessageFor(Exception e) {
		StringWriter errorMessage = new StringWriter();
		e.printStackTrace(new PrintWriter(errorMessage));
		return errorMessage.toString();
	}

	@Override
	public void handle(HttpExchange request) throws IOException {
		T body = body(request);
		log.info("Received {} {}", request.getRequestURI(), body);
		try {
			perform(simulator, body);
		} catch (Exception e) {
			sendError(request, body, e);
		}
		sendResponse(request, body, "");
	}

	public T body(HttpExchange request) throws IOException {
		InputStream requestBody = request.getRequestBody();
		InputStreamReader inputStreamReader = new InputStreamReader(requestBody);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = bufferedReader.readLine();
		return new Gson().fromJson(line, type);
	}

	public abstract void perform(VictorOwnedSimulator simulator, T body) throws IOException, InterruptedException;

	public void sendError(HttpExchange request, T body, Exception e) throws IOException {
		sendResponse(request, body, errorMessageFor(e));
	}

	public void sendResponse(HttpExchange request, T body, String message) throws IOException {
		request.sendResponseHeaders(200, 0);
		new OutputStreamWriter(request.getResponseBody()).append(message);
		request.close();
	}
}
