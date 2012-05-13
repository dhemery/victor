package com.dhemery.victor.configuration.generic;

public class SourcedMappedCache<K,V> extends MappedCache<K,V> implements SourcedCache<K,V> {
    @Override
    public V value(K key, CacheSource<K, V> source) {
        if(!hasKey(key)) store(key, source.value(key));
        return storedValue(key);
    }
}
