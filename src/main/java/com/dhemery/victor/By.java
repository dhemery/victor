package com.dhemery.victor;

/**
 * Identifies a set of views in an iOS application.
 *
 * @author Dale Emery
 */

public class By {
    /**
     * The name by which the Igor query engine registers with the Frank server.
     */
    public static final String IGOR_QUERY_ENGINE = "igor";

    /**
     * The name by which the Shelley query engine registers with the Frank server.
     */
    public static final String SHELLEY_QUERY_ENGINE = "shelley_compat";

    /**
     * The name by which the UIQuery query engine registers with the Frank server.
     */
    public static final String UIQUERY_QUERY_ENGINE = "uiquery";

    /**
     * The name of the selector engine to use to select the views identified by this {@code By}.
     */
    public final String selectorEngine;

    /**
     * A pattern by which the selector engine identifies a set of views.
     */
    public final String selector;

    /**
     * Uses the named selector engine and selector to identify views.
     * @param selectorEngine the name of the selector engine to use to select the views identified by this {@code By}.
     * @param selector a pattern by which the selector engine identifies a set of views.
     */
    public By(String selectorEngine, String selector) {
        this.selectorEngine = selectorEngine;
        this.selector = selector;
    }

    /**
     * @param igorQuery a query in Igor Query Language format.
     * @return a query that uses the Igor query engine to select views using the given query.
     */
    public static By igor(String igorQuery) {
        return new By(IGOR_QUERY_ENGINE, igorQuery);
    }

    /**
     * @param shelleyQuery a query in the format recognized by the Shelley query engine.
     * @return a query that uses the Shelley query engine to select views using the given query.
     */
    public static By shelley(String shelleyQuery) {
        return new By(SHELLEY_QUERY_ENGINE, shelleyQuery);
    }

    /**
     * @param uiQuery a query in the format recognized by the UIQuery query engine.
     * @return a query that uses the UIQuery query engine to select views using the given query.
     */
    public static By uiQuery(String uiQuery) {
        return new By(UIQUERY_QUERY_ENGINE, uiQuery);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", selectorEngine, selector);
    }
}
