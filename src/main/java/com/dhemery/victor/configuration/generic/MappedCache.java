package com.dhemery.victor.configuration.generic;

import java.util.HashMap;
import java.util.Map;

/**
 * A cache backed by a {@link HashMap}.
 * @param <K> the type of key.
 * @param <V> the type of value.
 */
public class MappedCache<K, V> implements Cache<K,V>{
    private final Map<K,V> valuesByKey = new HashMap<K,V>();

    /**
     * @param key a key.
     * @return whether the cache has a value for {@code key}.
     */
    protected boolean hasKey(K key) {
        return valuesByKey.containsKey(key);
    }

    /**
     * Associates a value with a key.
     * @param key a key.
     * @param value the value to associate with {@code key}.
     */
    protected void store(K key, V value) {
        valuesByKey.put(key, value);
    }

    /**
     * @param key a key.
     * @return the value associated with key, or null if no value is associated.
     */
    protected V storedValue(K key) {
        return valuesByKey.get(key);
    }

    @Override
    public V value(K key, V defaultValue) {
        if(!hasKey(key)) store(key, defaultValue);
        return storedValue(key);
    }
}
