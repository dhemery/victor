package com.dhemery.network;

import com.dhemery.publishing.Publisher;

public class PublishingResourceFactory implements ResourceFactory {
    private final Publisher publisher;
    private final ResourceFactory create;

    public PublishingResourceFactory(Publisher publisher, ResourceFactory resources){
        this.publisher = publisher;
        create = resources;
    }

    @Override
    public Resource resource(String protocol, String host, int port, String path) {
        Resource resource = create.resource(protocol, host, port, path);
        return new PublishingResource(publisher, resource);
    }
}
