package com.dhemery.osx;

//todo: Replace the embedded applescript commands with resource files.

/**
 * Interacts with an OS X application by running AppleScript programs with the {@code osascript} command.
 */
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
    private final AppleScriptShell apple;

    //todo: Discover the process name through the application's plist.
    /**
     * @param name the name of the application to interact with
     * @param processName the name of process in which the application is running
     */
    public ScriptableApplication(String name, String processName, AppleScriptShell shell) {
        this.apple = shell;
        activateApplication = String.format(ACTIVATE_APPLICATION, name);
        tellMenuBar = String.format(TELL_MENU_BAR_OF_PROCESS, processName);
    }

    @Override
    public void typeKey(char key, MetaKey metaKeys) {
        apple.script("Type Simulator Keys")
                .withLine(activateApplication)
                .withLine(String.format(STROKE_KEY_WITH_METAKEYS, key, metaKeys.down()))
                .build()
                .run();
    }

    @Override
    public void touchMenuItem(String menu, String item) {
        apple.script("Touch Simulator Menu")
                .withLine(activateApplication)
                .withLine(TELL_SYSTEM_EVENTS)
                .withLine(tellMenuBar)
                .withLine(String.format(CLICK_MENU_ITEM_OF_MENU, item, menu))
                .withLine(END_TELL)
                .withLine(END_TELL)
                .build()
                .run();
    }

    @Override
    public void touchMenuItem(String menu, String submenu, String item) {
        apple.script("Touch Simulator Menu")
                .withLine(activateApplication)
                .withLine(TELL_SYSTEM_EVENTS)
                .withLine(tellMenuBar)
                .withLine(String.format(TELL_MENU_ITEM_OF_MENU, submenu, menu))
                .withLine(String.format(CLICK_MENU_ITEM_OF_MENU, item, submenu))
                .withLine(END_TELL)
                .withLine(END_TELL)
                .withLine(END_TELL)
                .build()
                .run();
    }
}
