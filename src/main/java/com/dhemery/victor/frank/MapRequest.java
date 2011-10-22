package com.dhemery.victor.frank;

import com.dhemery.victor.http.Request;

public class MapRequest extends Request {
	public MapRequest(MapRequestBody body) {
		super("map", body);
	}

	public MapRequest(String query, Operation operation) {
		this(new MapRequestBody(query, operation));
	}
	
	public MapRequest(String query, String property) {
		this(query, new Operation(property));
	}	
}
