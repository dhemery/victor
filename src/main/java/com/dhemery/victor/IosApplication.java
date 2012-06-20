package com.dhemery.victor;

/**
 * Represents an application running on an iOS device.
 *
 * @author Dale Emery
 */
public interface IosApplication {

    /**
     * Send a message with arguments to the application delegate.
     *
     * @param name      the name of the message to send (an Objective-C query).
     * @param arguments arguments to send with the message.
     */
    @VictorEntryPoint
    String sendMessage(String name, Object... arguments);

    /**
     * @return whether the application is running.
     */
    @VictorEntryPoint
    Boolean isRunning();

    /**
     * @return the application's current orientation.
     */
    @VictorEntryPoint
    IosApplicationOrientation orientation();

    /**
     * @param query identifies a set of views.
     * @return a view driver that represents the identified views within this application.
     */
    @VictorEntryPoint
    IosView view(By query);
}
