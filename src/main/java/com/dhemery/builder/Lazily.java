package com.dhemery.builder;

public class Lazily {
    public static <T> Lazy<T> from(final Builder<T> builder) {
        return new Lazy<T>() {
            boolean built = false;
            T value;
            @Override
            public T get() {
                if(!built) {
                    value = builder.build();
                    built = true;
                }
                return value;
            }
        };
    }
}
