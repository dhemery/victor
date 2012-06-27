package com.dhemery.victor.os;

import java.util.ArrayList;
import java.util.List;

public class ScriptableApplication implements OsxApplication {
    private static final String MENU_ITEM_OF_MENU = "menu item \"%s\" of menu \"%s\"";
    private static final String TELL_SYSTEM_EVENTS = "tell application \"System Events\"";

    private static final String ACTIVATE_APPLICATION = "activate application \"%s\"";
    private static final String CLICK_MENU_ITEM_OF_MENU = "click " + MENU_ITEM_OF_MENU;
    private static final String END_TELL = "end tell";
    private static final String TELL_MENU_ITEM_OF_MENU = "tell " + MENU_ITEM_OF_MENU;
    private static final String TELL_MENU_BAR_OF_PROCESS = "tell menu bar of process \"%s\"";
    private static final String STROKE_KEY_WITH_METAKEYS = TELL_SYSTEM_EVENTS + " to keystroke \"%s\" using {%s}";

    private final String activateApplication;
    private final String tellMenuBar;

    public ScriptableApplication(String name, String processName) {
        activateApplication = String.format(ACTIVATE_APPLICATION, name);
        tellMenuBar = String.format(TELL_MENU_BAR_OF_PROCESS, processName);
    }

    @Override
    public void typeKey(char key, MetaKey metaKeys) {
        List<String> script = new ArrayList<String>();
        script.add(activateApplication);
        script.add(String.format(STROKE_KEY_WITH_METAKEYS, key, metaKeys.down()));
        new AppleScriptCommand(script).run();
    }

    @Override
    public void touchMenuItem(String menu, String item) {
        List<String> script = new ArrayList<String>();
        script.add(activateApplication);
        script.add(TELL_SYSTEM_EVENTS);
        script.add(tellMenuBar);
        script.add(String.format(CLICK_MENU_ITEM_OF_MENU, item, menu));
        script.add(END_TELL);
        script.add(END_TELL);
        new AppleScriptCommand(script).run();
    }

    @Override
    public void touchMenuItem(String menu, String submenu, String item) {
        List<String> script = new ArrayList<String>();
        script.add(activateApplication);
        script.add(TELL_SYSTEM_EVENTS);
        script.add(tellMenuBar);
        script.add(String.format(TELL_MENU_ITEM_OF_MENU, submenu, menu));
        script.add(String.format(CLICK_MENU_ITEM_OF_MENU, item, submenu));
        script.add(END_TELL);
        script.add(END_TELL);
        script.add(END_TELL);
        new AppleScriptCommand(script).run();
    }
}
