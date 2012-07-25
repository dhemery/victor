package com.dhemery.builder;

public class BuiltLazy<T> implements Lazy<T> {
    boolean built;
    T value;
    private final Builder<T> builder;

    public BuiltLazy(Builder<T> builder) {
        this.builder = builder;
        built = false;
    }

    @Override
    public T get() {
        if(!built) {
            value = builder.build();
            built = true;
        }
        return value;
    }
}
