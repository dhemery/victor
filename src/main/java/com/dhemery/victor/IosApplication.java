package com.dhemery.victor;

/**
 * Represents an application running on an iOS device.
 *
 * @author Dale Emery
 */
public interface IosApplication {
    enum Orientation {
        LANDSCAPE,
        PORTRAIT,
        /**
         * Victor cannot determine the application's orientation.
         */
        UNKNOWN,
    }

    /**
     * Send a message to the application delegate.
     *
     * @param name the name of the message to send (an Objective-C selector).
     */
    void sendMessage(String name);

    /**
     * Send a message with arguments to the application delegate.
     *
     * @param name      the name of the message to send (an Objective-C selector).
     * @param arguments arguments to send with the message.
     */
    void sendMessage(String name, Object... arguments);

    /**
     * @return whether the application is running.
     */
    Boolean isRunning();

    /**
     * @return the application's current orientation.
     */
    Orientation orientation();

    /**
     * @param query identifies a set of views.
     * @return a view driver that represents the identified views within this application.
     */
    IosView view(By query);
}
