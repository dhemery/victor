package com.dhemery.victor.frank;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhemery.victor.http.RequestBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MapRequestBody implements RequestBody {
	private static final Logger log = LoggerFactory.getLogger(MapRequestBody.class);
	public final String query;
	public final Operation operation;

	public MapRequestBody(String query, Operation operation) {
		this.query = query;
		this.operation = operation;
	}
	
	@Override
	public void writeTo(HttpURLConnection connection) throws IOException {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		log.debug("Posting body {}", json);
		OutputStream outputStream = connection.getOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		out.write(json);
		out.close();
	}
}
