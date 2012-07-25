package com.dhemery.victor;

public class ByLanguagePattern implements By {
    private final String language;
    private final String pattern;

    public ByLanguagePattern(String language, String pattern) {
        this.language = language;
        this.pattern = pattern;
    }

    @Override
    public String language() {
        return language;
    }

    @Override
    public String pattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return language + ' ' + pattern;
    }
}
