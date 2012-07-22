package com.dhemery.builder;

public class Lazily {
    public static <T> Lazy<T> from(final Builder<T> builder) {
        return new Lazy<T>() {
            @Override
            public T get() {
                return builder.build();
            }
        };
    }
}
