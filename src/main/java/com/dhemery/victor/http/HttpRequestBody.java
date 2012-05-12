package com.dhemery.victor.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;

/**
 * The body of an HTTP request.
 * Buy default, an {@code HttpRequestBody} is empty and is not written to the connection,
 * which causes the request to be sent via HTTP GET.
 * Derived classes may actually write bytes to the connection,
 * which causes the request to be sent vie HTTP PUT.
 *
 * @author Dale Emery
 */
public class HttpRequestBody {
    protected final transient Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Write the body of this request through the connection.
     * In this class, the method does nothing, which causes the request to be sent via HTTP GET.
     * Derived classes may override this method and actually write bytes,
     * which causes the message to be sent via HTTP PUT.
     *
     * @param connection the connection to write the request body to.
     */
    public void writeTo(HttpURLConnection connection) {
        log.trace("Sending empty body to {} via HTTP GET", connection.getURL());
    }

    @Override
    public String toString() {
        return "";
    }
}