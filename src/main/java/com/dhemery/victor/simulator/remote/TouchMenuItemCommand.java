package com.dhemery.victor.simulator.remote;

import com.dhemery.victor.http.HttpPostBody;

public class TouchMenuItemCommand extends HttpPostBody {
	public final String menuName;
	public final String menuItemName;

	public TouchMenuItemCommand(String menuName, String menuItemName) {
		this.menuName = menuName;
		this.menuItemName = menuItemName;
	}
	
	@Override
	public String toString() {
		return String.format("[menuName:%s][menuItemName:%s]", menuName, menuItemName);
	}
}