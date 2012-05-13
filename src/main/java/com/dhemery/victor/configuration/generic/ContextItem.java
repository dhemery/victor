package com.dhemery.victor.configuration.generic;

/**
 * An item in a context.
 */
public class ContextItem {
    private final String context;
    private final String name;

    /**
     * @param context the name of the context in which the item occurs.
     * @param name the name of the item within the context.
     */
    public ContextItem(String context, String name) {
        this.context = context;
        this.name = name;
    }

    public String context() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContextItem that = (ContextItem) o;
        return context.equals(that.context) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return 31 * context.hashCode() + name.hashCode();
    }

    public String name() {
        return name;
    }
}
