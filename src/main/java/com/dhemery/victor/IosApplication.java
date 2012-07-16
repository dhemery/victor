package com.dhemery.victor;

/**
 * A driver that interacts with an application running on an iOS device.
 *
 * @author Dale Emery
 */
public interface IosApplication {

    /**
     * Report whether the application is running.
     * @return whether the application is running.
     */
    boolean isRunning();

    /**
     * Report the application's orientation.
     * @return the application's orientation.
     */
    IosApplicationOrientation orientation();

    /**
     * Send a message with arguments to the application delegate.
     *
     * @param name      the name of the message to send (an Objective-C selector).
     * @param arguments arguments to send with the message.
     * @return          the application delegate's response.
     */
    String sendMessage(String name, Object... arguments);

    /**
     * Type text into the device's keyboard.
     * This method assumes that the keyboard is displayed.
     * @param text the text to type.
     */
    void typeIntoKeyboard(String text);
}
