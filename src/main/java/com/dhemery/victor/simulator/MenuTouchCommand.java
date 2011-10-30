package com.dhemery.victor.simulator;

import java.util.ArrayList;
import java.util.List;

public class MenuTouchCommand extends AppleScriptCommand {
	private static final String TOUCH_MENU_ITEM_TEMPLATE = "   click menu item \"%s\" of menu \"%s\" of menu bar of process \"iPhone Simulator\"";

	public MenuTouchCommand(String menuName, String menuItemName) {
		super(menuTouchScriptFor(menuName, menuItemName));
	}

	private static List<String> menuTouchScriptFor(String menuName, String menuItemName) {
		List<String> scriptLines = new ArrayList<String>();
		scriptLines.add("activate application \"iPhone Simulator\"");
		scriptLines.add("tell application \"System Events\"");
		scriptLines.add(String.format(TOUCH_MENU_ITEM_TEMPLATE, menuItemName, menuName));
		scriptLines.add("end tell");
		return scriptLines;
	}
}
