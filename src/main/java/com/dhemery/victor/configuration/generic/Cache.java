package com.dhemery.victor.configuration.generic;

public interface Cache<K, V> {
    /**
     * Returns the value associated with a key.
     * If the cache has no value for the key,
     * the cache stores {@code defaultValue}
     * and returns it.
     * @param key the key whose value to return.
     * @param defaultValue the value to associate with {@code key} if no value is already associated.
     * @return the value associated with {@code key}.
     */
    V value(K key, V defaultValue);
}
