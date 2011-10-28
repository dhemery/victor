package com.dhemery.victor.phone;

import java.util.ArrayList;
import java.util.List;

public class MenuTouchCommand extends AppleScriptCommand {
	private static final String TOUCH_MENU_ITEM_STEP_TEMPLATE = "   click menu item \"%s\" of menu \"%s\" of menu bar of process \"iPhone Simulator\"";
	private static final String ACTIVATE_SIMULATOR_STEP = "activate application \"iPhone Simulator\"";
	private static final String TELL_SYSTEM_EVENTS_STEP = "tell application \"System Events\"";
	private static final String END_TELL_STEP = "end tell";

	public MenuTouchCommand(String menuName, String menuItemName) {
		super(menuTouchScriptFor(menuName, menuItemName));
	}

	private static List<String> menuTouchScriptFor(String menuName, String menuItemName) {
	List<String> scriptLines = new ArrayList<String>();
	scriptLines.add(ACTIVATE_SIMULATOR_STEP);
	scriptLines.add(TELL_SYSTEM_EVENTS_STEP);
	scriptLines.add(String.format(TOUCH_MENU_ITEM_STEP_TEMPLATE, menuItemName, menuName));
	scriptLines.add(END_TELL_STEP);
	return scriptLines;
}

}
