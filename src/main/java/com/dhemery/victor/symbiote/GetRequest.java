package com.dhemery.victor.symbiote;

import java.io.IOException;
import java.net.HttpURLConnection;


public class GetRequest extends Request {
	public GetRequest(String verb) {
		super(verb);
	}

	@Override protected void writeBodyTo(HttpURLConnection connection) throws IOException {}
}
