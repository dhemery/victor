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
    String sendMessage(String name, Object... arguments);

    /**
     * @return whether the application is running.
     */
    Boolean isRunning();

    /**
     * @return the application's current orientation.
     */
    IosApplicationOrientation orientation();

    /**
     * Types text into the keyboard.
     * This method assumes that the keyboard is displayed.
     * @param text the text to type.
     */
    void typeIntoKeyboard(String text);

    /**
     * @param query identifies a set of views.
     * @return a view driver that represents the identified views within this application.
     */
    IosView view(By query);
}
