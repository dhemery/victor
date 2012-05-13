package com.dhemery.victor.configuration.generic;

public interface SourcedCache<K, V> extends Cache<K,V> {
    /**
     * Returns the value associated with a key.
     * If the cache has no value for the key,
     * the cache obtains a value from {@code source},
     * stores the obtained value,
     * and returns it.
     * @param key the key whose value to return.
     * @param source a source that can provide a value for key.
     * @return the value associated with {@code key}.
     */
    V value(K key, CacheSource<K, V> source);
}
