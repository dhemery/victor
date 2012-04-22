package com.dhemery.victor;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

/**
 * Identifies one or more views in an iOS application.
 *
 * @author Dale Emery
 */

public class By implements SelfDescribing {
    public final String selectorEngine;
    public final String selector;

    public By(String selectorEngine, String selector) {
        this.selectorEngine = selectorEngine;
        this.selector = selector;
    }

    public static By igor(String igorQuery) {
        return new By("igor", igorQuery);
    }

    public static By shelley(String shelleyQuery) {
        return new By("shelley_compat", shelleyQuery);
    }

    public static By uiQuery(String uiQuery) {
        return new By("uiquery", uiQuery);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", selectorEngine, selector);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(toString());
    }
}
