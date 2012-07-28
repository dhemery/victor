package com.dhemery.victor;

/**
 * An identifier that identifies a set of views in an iOS application.
 * A view identifier has two parts:
 * <ul>
 * <li>a <strong>language</strong> that defines the syntax of a pattern that matches views.</li>
 * <li>a <strong>pattern</strong> that matches a set of views in that language.</li>
 *</ul>
 */
public class IosViewIdentifier {
    private final String language;
    private final String pattern;

    /**
     * Create a view identifier that identifies views by a pattern in the specified language.
     * @param language the language that defines the syntax of the pattern
     * @param pattern a pattern in the specified language
     */
    public IosViewIdentifier(String language, String pattern) {
        this.language = language;
        this.pattern = pattern;
    }

    /**
     * The language that defines the syntax of this identifier's pattern.
     */
    public String language() {
        return language;
    }

    /**
     * A pattern in this identifier's language.
     */
    public String pattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return language + ' ' + pattern;
    }
}
