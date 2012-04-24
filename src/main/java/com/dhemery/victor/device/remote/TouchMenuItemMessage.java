package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpPostBody;

public class TouchMenuItemMessage extends HttpPostBody {
	public final String menuName;
	public final String menuItemName;

	public TouchMenuItemMessage(String menuName, String menuItemName) {
		this.menuName = menuName;
		this.menuItemName = menuItemName;
	}
	
	@Override
	public String toString() {
		return String.format("[menuName:%s][menuItemName:%s]", menuName, menuItemName);
	}
}