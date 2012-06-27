package com.dhemery.victor.os;

public enum MetaKey {
    COMMAND("command"),
    CONTROL("control"),
    OPTION("option"),
    SHIFT("shift");

    private final String keyName;

    MetaKey(String name) {
        keyName = name;
    }

    public String down() {
        return keyName + " down";
    }
}
