package com.dhemery.victor;

/**
 * A query that uses Shelley to find views that match a pattern.
 * For a Shelley query to find views,
 * the Shelley selector engine must be running in the Frankified iOS application
 * and registered with Frank under the name "shelley_compat".
 * If the libShelley.a library is linked into the application,
 * Shelley runs in the application,
 * registered under the required name.
 */
public class Shelley implements By {
    private final String pattern;

    protected Shelley(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String language() {
        return "shelley_compat";
    }

    @Override
    public String pattern() {
        return pattern;
    }

    /**
     * Create a query that uses Shelley to find views that match a pattern.
     * @param pattern a pattern recognized by the Shelley selector engine.
     */
    public static Shelley shelley(String pattern) {
        return new Shelley(pattern);
    }
}
