package com.dhemery.victor;

public class Shelley implements By {
    private final String pattern;

    public Shelley(String pattern) {
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

    public static Shelley shelley(String pattern) {
        return new Shelley(pattern);
    }
}
