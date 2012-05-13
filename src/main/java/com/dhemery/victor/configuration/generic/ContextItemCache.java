package com.dhemery.victor.configuration.generic;

public class ContextItemCache {
    private final SingleSourceMappedCache<ContextItem,String> cache;

    public ContextItemCache(CacheSource<ContextItem,String> source) {
        cache = new SingleSourceMappedCache<ContextItem, String>(source);
    }

    public String value(String context, String name) {
        return cache.value(key(context, name));
    }

    private ContextItem key(String context, String name) {
        return new ContextItem(context, name);
    }
}
