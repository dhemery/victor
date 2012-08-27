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
    public <T> T put(String path, Object body, Class<T> responseType) {
        String encodedBody = codec.encode(body);
        String encodedResponse = endpoint.put(path, encodedBody);
        return codec.decode(encodedResponse, responseType);
    }
}
