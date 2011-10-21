package com.dhemery.victor.symbiote;

public class MapRequest extends PostRequest {
	public MapRequest(String query, String property) {
		super("map", new MapRequestBody(query, new Operation(property)));
	}
}
