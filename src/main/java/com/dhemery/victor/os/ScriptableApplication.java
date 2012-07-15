package com.dhemery.victor.os;

import com.dhemery.victor.OSCommand;

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
    private final Shell shell;

    public ScriptableApplication(Shell shell, String name, String processName) {
        this.shell = shell;
        activateApplication = String.format(ACTIVATE_APPLICATION, name);
        tellMenuBar = String.format(TELL_MENU_BAR_OF_PROCESS, processName);
    }

    @Override
    public void typeKey(char key, MetaKey metaKeys) {
        OSCommand command = new AppleScriptCommand()
                .withLine(activateApplication)
                .withLine(String.format(STROKE_KEY_WITH_METAKEYS, key, metaKeys.down()))
                .describedAs("Type Simulator Keys");
        shell.run(command);
    }

    @Override
    public void touchMenuItem(String menu, String item) {
        OSCommand command = new AppleScriptCommand()
                .withLine(activateApplication)
                .withLine(TELL_SYSTEM_EVENTS)
                .withLine(tellMenuBar)
                .withLine(String.format(CLICK_MENU_ITEM_OF_MENU, item, menu))
                .withLine(END_TELL)
                .withLine(END_TELL)
                .describedAs("Touch Simulator Menu");
        shell.run(command);
    }

    @Override
    public void touchMenuItem(String menu, String submenu, String item) {
        OSCommand command = new AppleScriptCommand()
                .withLine(activateApplication)
                .withLine(TELL_SYSTEM_EVENTS)
                .withLine(tellMenuBar)
                .withLine(String.format(TELL_MENU_ITEM_OF_MENU, submenu, menu))
                .withLine(String.format(CLICK_MENU_ITEM_OF_MENU, item, submenu))
                .withLine(END_TELL)
                .withLine(END_TELL)
                .withLine(END_TELL)
                .describedAs("Touch Simulator Menu");
        shell.run(command);
    }
}
