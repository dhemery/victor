package com.dhemery.victor.simulator;

import com.dhemery.victor.http.HttpPostBody;

public class RemoteTouchMenuItemCommandBody extends HttpPostBody {
	public final String menuName;
	public final String menuItemName;

	public RemoteTouchMenuItemCommandBody(String menuName, String menuItemName) {
		this.menuName = menuName;
		this.menuItemName = menuItemName;
	}
	
	@Override
	public String toString() {
		return String.format("[menuName:%s][menuItemName:%s]", menuName, menuItemName);
	}
}
