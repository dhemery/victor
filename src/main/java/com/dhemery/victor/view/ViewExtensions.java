package com.dhemery.victor.view;

import com.dhemery.victor.Action;
import com.dhemery.victor.ViewDriver;
import org.hamcrest.Matcher;

public class ViewExtensions {
    public static Action<ViewDriver> flash() {
        return new Flash();
    }

    public static Matcher<ViewDriver> isPresent() {
        return new Present();
    }

    public static Matcher<ViewDriver> isVisible() {
        return new Visible();
    }

    public static Action<ViewDriver> touch() {
        return new Touch();
    }
}
