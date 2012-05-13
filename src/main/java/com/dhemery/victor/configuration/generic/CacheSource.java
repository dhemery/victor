package com.dhemery.victor.configuration.generic;

/**
 * Retrieves a value associated with a key.
 * @param <K> the type of key.
 * @param <V> the type of value.
 * @see Cache
 */
public interface CacheSource<K,V> {
    /**
     * @param key an object.
     * @return the value associated with {@code key}.
     */
    V value(K key);
}
