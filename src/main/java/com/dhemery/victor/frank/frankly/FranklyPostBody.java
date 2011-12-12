package com.dhemery.victor.frank.frankly;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The body of a Frank POST request.
 * Writes itself to the connection as a JSON string.
 * @author Dale Emery
 *
 */
public class FranklyPostBody extends FranklyRequestBody {
	private static final Logger log = LoggerFactory.getLogger(FranklyPostBody.class);

	/**
	 * Writes this request body to the connection as a JSON string.
	 * This causes the request to be sent via HTTP POST.
	 */
	@Override
	public void writeTo(HttpURLConnection connection) throws IOException {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String json = gson.toJson(this);
		log.debug("Writing body as Json: {}", json);
		OutputStream outputStream = connection.getOutputStream();
		OutputStreamWriter out = new OutputStreamWriter(outputStream);
		out.write(json);
		out.close();
	}
}
