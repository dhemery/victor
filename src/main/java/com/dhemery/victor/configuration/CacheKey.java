package com.dhemery.victor.configuration;

class CacheKey {
    private final String context;
    private final String item;

    public CacheKey(String context, String item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public boolean equals(Object other) {
        CacheKey cacheKey = (CacheKey) other;
        return (context.equals(cacheKey.context) && item.equals(cacheKey.item));
    }

    @Override
    public int hashCode() {
        return 31 * context.hashCode() + item.hashCode();
    }
}
