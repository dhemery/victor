package com.dhemery.victor.device.local;

import java.util.ArrayList;
import java.util.List;

/**
 * A command to perform a menu item in an OS X application running on this computer.
 *
 * @author Dale Emery
 */
public class TouchMenuItemCommand extends AppleScriptCommand {
    private static final String MENU_ITEM_OF_MENU = "menu item \"%s\" of menu \"%s\"";
    private static final String ACTIVATE_SIMULATOR = "activate application \"iPhone Simulator\"";
    private static final String CLICK_MENU_ITEM_OF_MENU = "click " + MENU_ITEM_OF_MENU;
    private static final String END_TELL = "end tell";
    private static final String TELL_MENU_ITEM_OF_MENU = "tell " + MENU_ITEM_OF_MENU;
    private static final String TELL_MENU_BAR = "tell menu bar of process \"iOS Simulator\"";
    private static final String TELL_SYSTEM_EVENTS = "tell application \"System Events\"";

    /**
     * @param menuName     the name of the menu to touch.
     * @param menuItemName the name of the menu item to touch.
     */
    public TouchMenuItemCommand(String menuName, String menuItemName) {
        super(menuTouchScriptFor(menuName, menuItemName));
    }

    /**
     * @param menuName     the name of the menu to touch.
     * @param menuItemName the name of the menu item to touch.
     * @param menuSubItemName the name of the menu subitem to touch.
     */
    public TouchMenuItemCommand(String menuName, String menuItemName, String menuSubItemName) {
        super(menuTouchScriptFor(menuName, menuItemName, menuSubItemName));
    }

    /**
     * Construct an AppleScript program to perform the menu message.
     *
     * @param menuName     the name of the menu to touch.
     * @param menuItemName the name of the menu item to touch.
     * @return A list of lines of AppleScript to perform the menu message.
     */
    private static List<String> menuTouchScriptFor(String menuName, String menuItemName) {
        List<String> scriptLines = new ArrayList<String>();
        scriptLines.add(ACTIVATE_SIMULATOR);
        scriptLines.add(TELL_SYSTEM_EVENTS);
        scriptLines.add(TELL_MENU_BAR);
        scriptLines.add(String.format(CLICK_MENU_ITEM_OF_MENU, menuItemName, menuName));
        scriptLines.add(END_TELL);
        scriptLines.add(END_TELL);
        return scriptLines;
    }

    /**
     * Construct an AppleScript program to perform the menu message.
     *
     * @param menuName     the name of the menu to touch.
     * @param menuItemName the name of the menu item to touch.
     * @return A list of lines of AppleScript to perform the menu message.
     */
    private static List<String> menuTouchScriptFor(String menuName, String menuItemName, String menuSubItemName) {
        List<String> scriptLines = new ArrayList<String>();
        scriptLines.add(ACTIVATE_SIMULATOR);
        scriptLines.add(TELL_SYSTEM_EVENTS);
        scriptLines.add(TELL_MENU_BAR);
        scriptLines.add(String.format(TELL_MENU_ITEM_OF_MENU, menuItemName, menuName));
        scriptLines.add(String.format(CLICK_MENU_ITEM_OF_MENU, menuSubItemName, menuItemName));
        scriptLines.add(END_TELL);
        scriptLines.add(END_TELL);
        scriptLines.add(END_TELL);
        return scriptLines;
    }
}
