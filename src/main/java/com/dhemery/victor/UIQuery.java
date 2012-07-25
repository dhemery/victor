package com.dhemery.victor;

/**
 * A query that uses UIQuery to find views that match a pattern.
 * For a UIQuery query to find views,
 * the UIQuery selector engine must be running in the Frankified iOS application
 * and registered with Frank under the name "uiquery".
 * Currently, UIQuery is part of Frank itself,
 * and runs in every Frankified application,
 * registered under the required name.
 */
public class UIQuery extends ByLanguagePattern {
    public static final String LANGUAGE = "uiquery";

    protected UIQuery(String pattern) {
        super(LANGUAGE, pattern);
    }

    /**
     * Create a query that uses UIQuery to find views that match a pattern.
     * @param pattern a pattern recognized by the UIQuery selector engine.
     */
    public static UIQuery uiquery(String pattern) {
        return new UIQuery(pattern);
    }
}
