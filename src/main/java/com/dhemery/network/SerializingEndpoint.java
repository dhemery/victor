package com.dhemery.network;

public interface SerializingEndpoint {
    <T> T get(String path, Class<T> responseType);
    <T> T put(String path, Object body, Class<T> responseType);
}
