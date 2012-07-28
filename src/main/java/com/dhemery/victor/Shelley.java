package com.dhemery.victor;

/**
 * Identifies views using a Shelley pattern.
 * For Frank to find views using Shelley identifiers,
 * the Shelley selector engine must be running in the Frankified iOS application
 * and registered with Frank under the name "shelley_compat".
 * If the libShelley.a library is linked into the application,
 * Shelley runs in the application,
 * registered under the required name.
 */
public class Shelley extends IosViewIdentifier {
    public static final String LANGUAGE = "shelley_compat";

    protected Shelley(String pattern) {
        super(LANGUAGE, pattern);
    }

    /**
     * Create a view identifier that identifies views using a Shelley pattern.
     * @param pattern a pattern recognized by the Shelley selector engine
     */
    public static Shelley shelley(String pattern) {
        return new Shelley(pattern);
    }
}
