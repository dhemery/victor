package com.dhemery.victor.configuration.generic;

public class SingleSourceMappedCache<K,V> extends MappedCache<K,V> implements SourcedCache<K,V> {
    private final CacheSource<K, V> source;

    public SingleSourceMappedCache(CacheSource<K, V> source) {
        this.source = source;
    }

    public V value(K key) {
        if(!hasKey(key)) store(key, source.value(key));
        return storedValue(key);
    }

    @Override
    public V value(K key, CacheSource<K, V> source) {
        if(!hasKey(key)) store(key, source.value(key));
        return storedValue(key);
    }
}
