package com.dhemery.victor.symbiote;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PostRequest extends Request {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private final PostRequestBody body;

	public PostRequest(String verb, PostRequestBody body) {
		super(verb);
		this.body = body;
	}

	@Override
	protected void writeBodyTo(HttpURLConnection connection) throws IOException {
		String json = body.toJson();
		log.debug("Posting body {}", json);
		OutputStream outputStream = connection.getOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		out.write(json);
		out.close();
	}
}
