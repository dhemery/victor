package com.dhemery.victor.configuration;

import java.util.HashMap;
import java.util.Map;

public class ContextItemCache {
    private static final Map<Key,String> cache = new HashMap<Key, String>();
    private final ContextItemFetcher fetcher;

    public ContextItemCache(ContextItemFetcher fetcher) {
        this.fetcher = fetcher;
    }

    public String value(String context, String item) {
        Key key = new Key(context, item);
        if(!cache.containsKey(key)) {
            cache.put(key, fetcher.fetch(context, item));
        }
        return cache.get(key);
    }

    private class Key {
        private final String context;
        private final String item;

        public Key(String context, String item) {
            this.context = context;
            this.item = item;
        }

        @Override
        public boolean equals(Object other) {
            Key key = (Key) other;
            return (context.equals(key.context) && item.equals(key.item));
        }

        @Override
        public int hashCode() {
            return 31 * context.hashCode() + item.hashCode();
        }
    }
}
