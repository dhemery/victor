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
     * The name of the query engine that will select the views.
     */
    private final String engine;

    /**
     * A pattern by which the query engine identifies a set of views.
     */
    private final String pattern;

    /**
     * @param engine the name of the query engine that will select the views.
     * @param pattern a pattern by which the query engine identifies a set of views.
     */
    public By(String engine, String pattern) {
        this.engine = engine;
        this.pattern = pattern;
    }

    /**
     * @param pattern a pattern in the Igor Query Language.
     * @return a query that uses Igor to select views that match the pattern.
     */
    @VictorEntryPoint
    public static By igor(String pattern) {
        return new By(IGOR_QUERY_ENGINE, pattern);
    }

    /**
     * @param pattern a pattern in the Shelley query language.
     * @return a query that uses Shelley to select views that match the pattern.
     */
    @VictorEntryPoint
    public static By shelley(String pattern) {
        return new By(SHELLEY_QUERY_ENGINE, pattern);
    }

    /**
     * @param pattern a pattern in the UIQuery query language.
     * @return a query that uses UIQuery to select views that match the pattern.
     */
    @VictorEntryPoint
    public static By uiQuery(String pattern) {
        return new By(UIQUERY_QUERY_ENGINE, pattern);
    }

    public String engine() {
        return engine;
    }

    public String pattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", engine, pattern);
    }
}
