package com.dhemery.victor.frank;

import com.dhemery.victor.By;
import com.dhemery.victor.IosView;
import com.dhemery.victor.frank.frankly.MessageResponse;
import com.dhemery.victor.frank.frankly.MessageResponseParser;
import com.dhemery.victor.frank.frankly.PingRequest;
import com.dhemery.victor.http.HttpRequest;
import com.dhemery.victor.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Interacts with an iOS application via a Frank server embedded into the application.
 *
 * @author Dale Emery
 */
public class FrankAgent {
    private final String frankServerUrl;
    private final Gson gson;
    private Set<MessageListener> listeners = new HashSet<MessageListener>();

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
        notifyWillSendRequest(request);
        HttpResponse response = request.sendTo(frankServerUrl);
        T results = gson.fromJson(response.body(), resultsClass);
        notifyReceivedResponse(request, results);
        return results;
    }

    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }

    private void notifyWillSendRequest(HttpRequest request) {
        for(MessageListener listener : listeners) {
            listener.willSendRequest(request);
        }
    }

    private <T> void notifyReceivedResponse(HttpRequest request, T response) {
        for(MessageListener listener : listeners) {
            listener.receivedResponse(request, response);
        }
    }

    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    @Override
    public String toString() {
        return String.format("the Frank server at %s", frankServerUrl);
    }

    public IosView view(By query) {
        return new FrankIosView(this, query);
    }
}
