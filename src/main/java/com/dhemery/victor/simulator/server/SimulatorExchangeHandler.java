package com.dhemery.victor.simulator.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.simulator.local.LocalSimulator;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class SimulatorExchangeHandler<T> implements HttpHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final LocalSimulator simulator;
	private final Class<T> type;

	public SimulatorExchangeHandler(LocalSimulator simulator, Class<T> type) {
		this.simulator = simulator;
		this.type = type;
	}

	public T body(HttpExchange request) throws IOException {
		InputStream requestBody = request.getRequestBody();
		InputStreamReader requestReader = new InputStreamReader(requestBody);
		BufferedReader bufferedReader = new BufferedReader(requestReader);
		String line = bufferedReader.readLine();
		requestReader.close();
		return new Gson().fromJson(line, type);
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
		sendResponse(request, body, "OK");
	}

	public abstract void perform(LocalSimulator simulator, T body) throws IOException, InterruptedException;

	public void sendError(HttpExchange request, T body, Exception e) throws IOException {
		sendResponse(request, body, errorMessageFor(e));
	}

	public void sendResponse(HttpExchange request, T body, String message) throws IOException {
		String response = new StringBuilder()
							.append(request.getRequestURI())
							.append(" ")
							.append(body)
							.append(" ")
							.append(message)
							.toString();
		request.sendResponseHeaders(200, response.length());
		OutputStream responseBody = request.getResponseBody();
		OutputStreamWriter responseWriter = new OutputStreamWriter(responseBody);
		responseWriter.append(response);
		responseWriter.close();
		request.close();
	}
}
