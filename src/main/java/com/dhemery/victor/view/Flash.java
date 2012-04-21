package com.dhemery.victor.view;

import com.dhemery.polling.Action;
import com.dhemery.victor.ViewDriver;

public class Flash implements Action<ViewDriver> {
    @Override
    public void executeOn(ViewDriver view) {
        view.call("flash");
    }
}
