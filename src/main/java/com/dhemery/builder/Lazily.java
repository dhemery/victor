package com.dhemery.builder;

public class Lazily {
    public static <T> Lazy<T> get(final Supplier<T> supplier) {
        return new BuiltLazy<T>(new Builder<T>() {
            @Override
            public T build() {
                return supplier.get();
            }
        });
    }

    public static <T> Lazy<T> build(final Builder<T> builder) {
        return new BuiltLazy<T>(builder);
    }
}
