package com.dhemery.osx;

/**
 * Enumeration of Mac keyboard meta keys.
 */
public enum MetaKey {
    /**
     * The command (CMD) key.
     * Sometimes called the "Apple" key.
     */
    COMMAND("command"),

    /**
     * The control (CTRL) key.
     */
    CONTROL("control"),

    /**
     * The option (OPT) key.
     * Sometimes called the ALT key.
     */
    OPTION("option"),

    /**
     * The shift (SHFT) key.
     */
    SHIFT("shift");

    private final String keyName;

    MetaKey(String name) {
        keyName = name;
    }

    //todo: Describe this better.
    /**
     * @return an AppleScript descriptor for holding down the meta key during a keystroke.
     */
    public String down() {
        return keyName + " down";
    }
}
