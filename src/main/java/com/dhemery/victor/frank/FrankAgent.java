package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.frankly.PingRequest;
import com.dhemery.victor.frank.messages.MessageResponse;
import com.dhemery.victor.frank.messages.MessageResponseParser;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interacts with an iOS application via a Frank server embedded into the application.
 *
 * @author Dale Emery
 */
public class FrankAgent {
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
     * @return whether the Frank server is running.
     */
    public Boolean isRunning() {
        try {
            new PingRequest().sendTo(frankServerUrl);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public MessageResponse sendMessageRequest(HttpRequest request) {
        return sendRequest(request, MessageResponse.class);

    }

    public <T> T sendRequest(HttpRequest request, Class<T> resultsClass) {
        HttpResponse response = request.sendTo(frankServerUrl);
        T results = gson.fromJson(response.body(), resultsClass);
        log.trace("Results from {} ==> {}", request, results);
        return results;
    }

    @Override
    public String toString() {
        return String.format("the Frank server at %s", frankServerUrl);
    }

    public IosView view(By query) {
        return new FrankIosView(this, query);
    }
}
