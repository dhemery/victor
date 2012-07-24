package com.dhemery.network;

import com.dhemery.network.events.*;
import com.dhemery.publishing.Publisher;

import java.net.URL;

public class PublishingResource implements Resource {
    private final Publisher publisher;
    private final Resource resource;

    public PublishingResource(Publisher publisher, Resource resource){
        this.publisher = publisher;
        this.resource = resource;
    }

    @Override
    public URL url() {
        return resource.url();
    }

    @Override
    public String get() {
        publisher.publish(new WillSendGet(resource));
        String response;
        try {
            response = resource.get();
        } catch (NetworkKitException cause) {
            publisher.publish(new GetThrew(resource, cause));
            throw cause;
        }
        publisher.publish(new GetResponded(resource, response));
        return response;
    }

    @Override
    public String put(String message) {
        publisher.publish(new WillSendPut(resource, message));
        String response; try {
            response = resource.put(message);
        } catch (NetworkKitException cause) {
            publisher.publish(new PutThrew(resource, message, cause));
            throw cause;
        }
        publisher.publish(new PutResponded(resource, message, response));
        return response;
    }
}
