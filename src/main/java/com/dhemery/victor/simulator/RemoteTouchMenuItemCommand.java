package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpRequest;

public class RemoteTouchMenuItemCommand extends HttpRequest {
	public RemoteTouchMenuItemCommand(String menuName, String menuItemName) {
		super("touchMenuItem", new RemoteTouchMenuItemCommandBody(menuName, menuItemName));
	}
}
