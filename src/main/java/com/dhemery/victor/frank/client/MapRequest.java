package com.dhemery.victor.frank.client;

import com.dhemery.victor.frank.uiquery.MapCommand;
import com.dhemery.victor.frank.uiquery.Operation;
import com.dhemery.victor.http.Request;

public class MapRequest extends Request {
	public MapRequest(MapCommand body) {
		super("map", body);
	}

	public MapRequest(String query, Operation operation) {
		this(new MapCommand(query, operation));
	}
	
	public MapRequest(String query, String property) {
		this(query, new Operation(property));
	}	
}
