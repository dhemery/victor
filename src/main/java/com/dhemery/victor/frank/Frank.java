package com.dhemery.victor.frank;

import com.dhemery.network.Endpoint;

import java.util.List;

/**
 * An agent that can communicate with a Frank server.
 */
public interface Frank {
    //TODO: explain null return value.
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

    //TODO: explain empty list and null value.
    /**
     * Send a message to a set of views.
     * @param engine the name of the selector engine to select recipients for the message
     * @param query the pattern for the selector engine to use to select recipients
     * @param name the name of the message
     * @param arguments the message arguments
     * @return the list of responses, one from each recipient
     */
    List<String> map(String engine, String query, String name, Object... arguments);

    /**
     * Report the application's orientation.
     */
    String orientation();

    /**
     * Report whether the Frank server responds to requests.
     */
    boolean ping();

    /**
     * Type text using the device's keyboard.
     */
    void typeIntoKeyboard(String text);
}
