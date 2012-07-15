package com.dhemery.victor;

/**
 * A query that matches a set of views in an iOS application.
 * A query is made of two parts:
 * <ul>
 * <li>a language that defines the syntax of a pattern that matches views.</li>
 * <li>a pattern that matches a set of views in that language.</li>
 *</ul>
 * @author Dale Emery
 */
public interface By {
    /**
     * The name of the pattern's language.
     * @return the name of the pattern's language.
     */
    String language();

    /**
     * A pattern that matches a set of views.
     * @return a pattern that matches a set of views.
     */
    String pattern();
}
