package com.dhemery.victor;

/**
 * Identifies views using a UIQuery pattern.
 * For Frank to find views using UIQuery identifiers,
 * the UIQuery selector engine must be running in the Frankified iOS application
 * and registered with Frank under the name "uiquery".
 * Currently, UIQuery is part of Frank itself,
 * and runs in every Frankified application,
 * registered under the required name.
 */
public class UIQuery extends IosViewIdentifier {
    public static final String LANGUAGE = "uiquery";

    protected UIQuery(String pattern) {
        super(LANGUAGE, pattern);
    }

    /**
     * Create an identifier that identifies views using a UIQuery pattern.
     * @param pattern a pattern recognized by the UIQuery selector engine
     */
    public static UIQuery uiquery(String pattern) {
        return new UIQuery(pattern);
    }
}
