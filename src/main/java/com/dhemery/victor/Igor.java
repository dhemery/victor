package com.dhemery.victor;

/**
 * A query that uses Igor to find views that match a pattern.
 * For an Igor query to find views,
 * the Igor selector engine must be running in the Frankified iOS application
 * and registered with Frank under the name "igor".
 * If libIgor.a is linked into the application,
 * Igor runs in the application,
 * registered under the required name.
 */
public class Igor implements By {
    private final String pattern;

    protected Igor(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String language() {
        return "igor";
    }

    @Override
    public String pattern() {
        return pattern;
    }

    /**
     * Create a query that uses the Igor selector engine to find views that match a pattern.
     * @param pattern a pattern in the Igor Query Language.
     * @return a query that uses the Igor selector engine to find views that match  {@code pattern}.
     */
    public static Igor igor(String pattern) {
        return new Igor(pattern);
    }
}
