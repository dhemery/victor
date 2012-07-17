package com.dhemery.victor.frank;

import com.dhemery.victor.io.Endpoint;
import com.dhemery.victor.io.Protocol;

import java.util.List;

/**
 * An agent that can communicate with a Frank server.
 */
public interface Frank {
    /**
     * Send a message to the application delegate.
     * @param name the name of the message.
     * @param arguments the message arguments.
     * @return the application delegate's response to the message.
     */
    String appExec(String name, Object... arguments);

    /**
     * The endpoint through which this Frank agent communicates with the Frank server.
     */
    Endpoint endpoint();

    /**
     * Send a message to a set of iOS views.
     * @param engine the selector engine that will find views to which to send the message.
     * @param query a query that identifies a set of views.
     * @param name the name of the message.
     * @param arguments the message arguments.
     * @return the list of responses returned by the views.
     */
    List<String> map(String engine, String query, String name, Object... arguments);

    /**
     * Report the application's orientation.
     * @return the application's orientation as a string.
     */
    String orientation();

    /**
     * Report whether the application responds to requests.
     * @return whether the application responds to requests.
     */
    boolean ping();

    /**
     * The protocol through which this agent communicates with the Frank server.
     */
    Protocol protocol();

    /**
     * Type text using the device's keyboard.
     * @param text the text to type.
     */
    void typeIntoKeyboard(String text);
}
