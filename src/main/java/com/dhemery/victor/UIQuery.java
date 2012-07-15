package com.dhemery.victor;

public class UIQuery implements By {
    private final String pattern;

    public UIQuery(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String language() {
        return "uiquery";
    }

    @Override
    public String pattern() {
        return pattern;
    }

    public static UIQuery uiquery(String pattern) {
        return new UIQuery(pattern);
    }
}
