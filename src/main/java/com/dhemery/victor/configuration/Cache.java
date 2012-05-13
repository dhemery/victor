package com.dhemery.victor.configuration;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private static final Map<CacheKey,String> cache = new HashMap<CacheKey,String>();
    private final Fetcher fetcher;

    public Cache(Fetcher fetcher) {
        this.fetcher = fetcher;
    }

    public String value(String context, String item) {
        CacheKey key = new CacheKey(context, item);
        if(!cache.containsKey(key)) {
            cache.put(key, fetcher.fetch(context, item));
        }
        return cache.get(key);
    }
}
