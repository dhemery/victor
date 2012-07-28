package com.dhemery.victor;

/**
 * Identifies views using an Igor pattern.
 * For an Igor identifier to find views,
 * the Igor selector engine must be running in the Frankified iOS application
 * and registered with Frank under the name "igor".
 * If libIgor.a is linked into the application,
 * Igor runs in the application,
 * registered under the required name.
 */
public class Igor extends IosViewIdentifier {
    public static final String LANGUAGE = "igor";

    protected Igor(String pattern) {
        super(LANGUAGE, pattern);
    }

    /**
     * Create a view identifier that uses an Igor pattern to identify views.
     * @param pattern a pattern in the Igor Query Language
     */
    public static Igor igor(String pattern) {
        return new Igor(pattern);
    }
}
