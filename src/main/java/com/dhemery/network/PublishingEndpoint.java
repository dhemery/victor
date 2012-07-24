package com.dhemery.network;

import com.dhemery.network.events.*;
import com.dhemery.publishing.Publisher;

public class PublishingEndpoint implements Endpoint {
    private final Publisher publisher;
    private final Endpoint endpoint;

    public PublishingEndpoint(Publisher publisher, Endpoint endpoint){
        this.publisher = publisher;
        this.endpoint = endpoint;
    }

    @Override
    public String host() {
        return endpoint.host();
    }

    @Override
    public int port() {
        return endpoint.port();
    }

    @Override
    public String get(String path) {
        publisher.publish(new WillSendGet(path));
        String response;
        try {
            response = endpoint.get(path);
        } catch (NetworkKitException cause) {
            publisher.publish(new GetThrew(path, cause));
            throw cause;
        }
        publisher.publish(new GetResponded(path, response));
        return response;
    }

    @Override
    public String put(String path, String message) {
        publisher.publish(new WillSendPut(path, message));
        String response; try {
            response = endpoint.put(path, message);
        } catch (NetworkKitException cause) {
            publisher.publish(new PutThrew(path, message, cause));
            throw cause;
        }
        publisher.publish(new PutResponded(path, message, response));
        return response;
    }
}
