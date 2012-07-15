package com.dhemery.victor;

public class Igor implements By {
    private final String pattern;

    public Igor(String pattern) {
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

    public static Igor igor(String pattern) {
        return new Igor(pattern);
    }
}
