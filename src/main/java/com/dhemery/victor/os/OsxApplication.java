package com.dhemery.victor.os;

public interface OsxApplication {
    void touchMenuItem(String menu, String item);
    void touchMenuItem(String menu, String submenu, String item);
    void typeKey(char key, MetaKey metaKey);
}
