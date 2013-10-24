package com.dhemery.victor.frank;

import java.util.List;

/**
 * An agent that can communicate with a Frank server.
 */
public interface Frank {
    /**
     * Report whether accessibility is enabled in the device.
     * Note that Frank automatically enables accessibility,
     * so this method is obsolete.
     */
    boolean accessibilityCheck();

    //TODO: explain null return value.
    /**
     * Send a message to the application delegate.
     * @param name the name of the message.
     * @param arguments the message arguments.
     * @return the application delegate's response to the message.
     */
    String appExec(String name, Object... arguments);

    //todo: Describe the plist files that define what properties are dumped.
    /**
     * Retrieve a JSON description of the tree of views in the application's main window.
     */
    String dump();

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
     * Type text using the device's keyboard.
     */
    void typeIntoKeyboard(String text);
}
