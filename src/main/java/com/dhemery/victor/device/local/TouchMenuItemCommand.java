package com.dhemery.victor.device.local;

import java.util.ArrayList;
import java.util.List;

/**
 * A command to perform a menu item in an OS X application running on this computer.
 * @author Dale Emery
 *
 */
public class TouchMenuItemCommand extends AppleScriptCommand {
	private static final String TOUCH_MENU_ITEM_TEMPLATE = "   click menu item \"%s\" of menu \"%s\" of menu bar of process \"iOS Simulator\"";

	/**
	 * @param menuName the name of the menu to touch.
	 * @param menuItemName the name of the menu item to touch.
	 */
	public TouchMenuItemCommand(String menuName, String menuItemName) {
		super(menuTouchScriptFor(menuName, menuItemName));
	}

	/**
	 * Construct an AppleScript program to perform the menu message.
     * @param menuName the name of the menu to touch.
     * @param menuItemName the name of the menu item to touch.
     * @return A list of lines of AppleScript to perform the menu message.
	 */
	private static List<String> menuTouchScriptFor(String menuName, String menuItemName) {
		List<String> scriptLines = new ArrayList<String>();
		scriptLines.add("activate application \"iPhone Simulator\"");
		scriptLines.add("tell application \"System Events\"");
		scriptLines.add(String.format(TOUCH_MENU_ITEM_TEMPLATE, menuItemName, menuName));
		scriptLines.add("end tell");
		return scriptLines;
	}
}
