package com.dhemery.victor;

/**
 * A query that matches a set of views in an iOS application.
 * A query has two parts:
 * <ul>
 * <li>a <strong>language</strong> that defines the syntax of a pattern that matches views.</li>
 * <li>a <strong>pattern</strong> that matches a set of views in that language.</li>
 *</ul>
 * @author Dale Emery
 */
public interface By {
    /**
     * The name of the query's language.
     */
    String language();

    /**
     * A pattern that matches a set of views.
     */
    String pattern();
}
