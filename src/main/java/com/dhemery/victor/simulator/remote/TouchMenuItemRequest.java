package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpRequest;

public class TouchMenuItemRequest extends HttpRequest {
	public static final String VERB = "touchMenuItem";

	public TouchMenuItemRequest(String menuName, String menuItemName) {
		super(VERB, new TouchMenuItemCommand(menuName, menuItemName));
	}
}