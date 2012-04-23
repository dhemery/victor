package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.application.IosApplicationAgent;
import com.dhemery.victor.application.OrientationResponse;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
import com.dhemery.victor.message.Message;
import com.dhemery.victor.message.MessageResponse;
import com.dhemery.victor.view.IosViewAgent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An agent that interacts with an iOS application via a Frank server embedded into the application.
 * @author Dale Emery
 *
 */
public class FrankAgent implements IosViewAgent, IosApplicationAgent {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String serverUrl;
    private final Gson gson;

    /**
     * @param serverUrl The URL where the Frank server listens for requests.
     */
    public FrankAgent(String serverUrl) {
        this.serverUrl = serverUrl;
        gson = new GsonBuilder()
                .registerTypeAdapter(MessageResponse.class, new MessageResponseParser())
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Sends a GET request to the Frank server.
     * @return true if the Frank server responds to the request, otherwise false.
     */
    @Override
    public boolean isReady() {
        new PingRequest().sendTo(serverUrl);
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
        HttpResponse response = request.sendTo(serverUrl);
        T results = gson.fromJson(response.body(), resultsClass);
        log.trace("Results from {} ==> {}", request, results);
        return results;
    }

    @Override
    public String toString() {
        return String.format("Frank client (%s)", serverUrl);
    }
}