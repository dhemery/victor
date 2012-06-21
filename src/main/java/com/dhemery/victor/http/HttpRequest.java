package com.dhemery.victor.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A request to an HTTP server.
 *
 * @author Dale Emery
 */
public class HttpRequest {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String verb;
    private final HttpRequestBody body;

    /**
     * @param verb the path portion of the URL to which to send the request.
     */
    public HttpRequest(String verb) {
        this(verb, new HttpGetBody());
    }

    /**
     * @param verb the path portion of the URL to which to send the request.
     * @param body the body of the request.
     */
    public HttpRequest(String verb, HttpRequestBody body) {
        this.verb = verb;
        this.body = body;
    }

    /**
     * Send this request to the HTTP server and return the server's response.
     * If the body writes bytes to the connection,
     * the request is sent as a POST request.
     * Otherwise it is sent as a GET request.
     *
     * @param serverUrl The URL where the HTTP server listens for requests.
     * @return The HTTP server's response to the request.
     */
    public HttpResponse sendTo(String serverUrl) {
        URL url = urlFor(serverUrl, verb);
        log.trace("Sending: {} {}", url.toString(), this);
        HttpConnection connection = new HttpConnection(url);
        return connection.send(body);
    }

    protected URL urlFor(String serverUrl, String verb) {
        String fullUrl = String.format("%s/%s", serverUrl, verb);
        try {
            return new URL(fullUrl);
        } catch (MalformedURLException cause) {
            throw new HttpException(cause);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s", verb, body);
    }
}
