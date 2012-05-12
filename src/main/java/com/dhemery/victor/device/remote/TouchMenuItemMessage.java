package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpPostBody;

public class TouchMenuItemMessage extends HttpPostBody {
    private final String menuName;
    private final String menuItemName;

    public TouchMenuItemMessage(String menuName, String menuItemName) {
        this.menuName = menuName;
        this.menuItemName = menuItemName;
    }

    public String menuItemName() {
        return menuItemName;
    }

    public String menuName() {
        return menuName;
    }

    @Override
    public String toString() {
        return String.format("[menuName:%s][menuItemName:%s]", menuName, menuItemName);
    }
}