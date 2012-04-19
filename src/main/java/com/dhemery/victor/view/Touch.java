package com.dhemery.victor.view;

import com.dhemery.victor.Action;
import com.dhemery.victor.ViewDriver;

public class Touch implements Action<ViewDriver> {
    @Override
    public void executeOn(ViewDriver view) {
        view.call("touch");
    }
}
