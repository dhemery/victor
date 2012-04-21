package com.dhemery.victor.view;

import com.dhemery.polling.Action;
import com.dhemery.victor.ViewDriver;
import org.hamcrest.Matcher;

public class ViewExtensions {
    public static Action<ViewDriver> flash() {
        return new Flash();
    }

    public static Matcher<ViewDriver> present() {
        return new Present();
    }

    public static Action<ViewDriver> touch() {
        return new Touch();
    }

    public static Matcher<ViewDriver> visible() {
        return new Visible();
    }
}
