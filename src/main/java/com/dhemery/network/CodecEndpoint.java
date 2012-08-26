package com.dhemery.network;

import com.dhemery.serializing.Codec;

public class CodecEndpoint implements SerializingEndpoint {
    private final Endpoint endpoint;
    private final Codec codec;

    public CodecEndpoint(Endpoint endpoint, Codec codec) {
        this.endpoint = endpoint;
        this.codec = codec;
    }

    @Override
    public <T> T get(String path, Class<T> responseType) {
        String rawResponse = endpoint.get(path);
        return codec.decode(rawResponse, responseType);
    }

    @Override
    public <T> T put(String path, Object payload, Class<T> responseType) {
        String message = codec.encode(payload);
        String rawResponse = endpoint.put(path, message);
        return codec.decode(rawResponse, responseType);
    }
}
