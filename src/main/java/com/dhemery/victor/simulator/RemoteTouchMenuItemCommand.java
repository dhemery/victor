package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpRequest;

public class RemoteTouchMenuItemCommand extends HttpRequest {
	public static final String VERB = "touchMenuItem";

	public RemoteTouchMenuItemCommand(String menuName, String menuItemName) {
		super(VERB, new RemoteTouchMenuItemCommandBody(menuName, menuItemName));
	}
}
