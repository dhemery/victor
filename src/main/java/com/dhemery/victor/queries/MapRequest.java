package com.dhemery.victor.queries;


public class MapRequest extends PostRequest {
	public MapRequest(String locator, String property) {
		super("map", new MapRequestBody(locator, new Operation(property)));
	}
}
