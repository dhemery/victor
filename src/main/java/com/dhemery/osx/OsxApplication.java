package com.dhemery.osx;

//todo: Allow touching arbitrarily deep menu items.
/**
 * Interacts with an OS X application.
 */
public interface OsxApplication {
    /**
     * Touch an item in a top-level menu of the application.
     * @param menu the name of a top-level menu
     * @param item the name of a menu item in the top-level menu
     */
    void touchMenuItem(String menu, String item);

    /**
     * Touch an item in a submenu of a top-level menu of the application.
     * @param menu the name of a top-level menu
     * @param submenu the name of a submenu of the top-level menu
     * @param item the name of a menu item item in the submenu
     */
    void touchMenuItem(String menu, String submenu, String item);
}
