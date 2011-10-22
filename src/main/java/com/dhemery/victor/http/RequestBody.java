package com.dhemery.victor.http;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface RequestBody {
	void writeTo(HttpURLConnection connection) throws IOException;
}
