package com.dhemery.victor.device.remote;

import com.dhemery.victor.http.HttpRequest;

public class TouchMenuItemRequest extends HttpRequest {
    public static final String VERB = "touchMenuItem";

    public TouchMenuItemRequest(String menuName, String menuItemName) {
        super(VERB, new TouchMenuItemMessage(menuName, menuItemName));
    }
}
