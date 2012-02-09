package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpPostBody;
import com.dhemery.victor.http.HttpRequest;

public class RemoteTouchMenuItemCommand extends HttpRequest {
	public static final String VERB = "touchMenuItem";

	public RemoteTouchMenuItemCommand(String menuName, String menuItemName) {
		super(VERB, new Body(menuName, menuItemName));
	}

	public static class Body extends HttpPostBody {
		public final String menuName;
		public final String menuItemName;

		public Body(String menuName, String menuItemName) {
			this.menuName = menuName;
			this.menuItemName = menuItemName;
		}
		
		@Override
		public String toString() {
			return String.format("[menuName:%s][menuItemName:%s]", menuName, menuItemName);
		}
	}
}
