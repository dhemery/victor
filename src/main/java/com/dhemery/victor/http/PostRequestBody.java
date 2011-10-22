package com.dhemery.victor.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PostRequestBody extends RequestBody {
	private static final Logger log = LoggerFactory.getLogger(PostRequestBody.class);

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
