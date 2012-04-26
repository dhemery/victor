package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.messages.Message;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.MessageResponseParser;
import com.dhemery.victor.frank.messages.OrientationResponse;
import com.dhemery.victor.frank.frankly.*;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An agent that interacts with an iOS application via a Frank server embedded into the application.
 *
 * @author Dale Emery
 */
public class FrankAgent implements FrankViewAgent, FrankApplicationAgent {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String frankServerUrl;
    private final Gson gson;

    /**
     * @param frankServerUrl The URL where the Frank server listens for requests.
     */
    public FrankAgent(String frankServerUrl) {
        this.frankServerUrl = frankServerUrl;
        gson = new GsonBuilder()
                .registerTypeAdapter(MessageResponse.class, new MessageResponseParser())
                .disableHtmlEscaping()
                .create();
    }

    /**
     * @return true if the Frank server is ready to respond to requests.
     */
    public boolean isReady() {
        new PingRequest().sendTo(frankServerUrl);
        return true;
    }

    @Override
    public OrientationResponse orientation() {
        return sendRequest(new ApplicationOrientationRequest(), OrientationResponse.class);
    }

    @Override
    public MessageResponse sendApplicationMessage(Message message) {
        return sendMessageRequest(new ApplicationMessageRequest(message));
    }

    private MessageResponse sendMessageRequest(HttpRequest request) {
        return sendRequest(request, MessageResponse.class);
    }

    @Override
    public MessageResponse sendViewMessage(By query, Message message) {
        return sendMessageRequest(new ViewMessageRequest(query, message));
    }

    private <T> T sendRequest(HttpRequest request, Class<T> resultsClass) {
        HttpResponse response = request.sendTo(frankServerUrl);
        T results = gson.fromJson(response.body(), resultsClass);
        log.trace("Results from {} ==> {}", request, results);
        return results;
    }

    @Override
    public String toString() {
        return String.format("Frank client (%s)", frankServerUrl);
    }

    @Override
    public IosView view(By query) {
        return new FrankIosView(this, query);
    }
}
