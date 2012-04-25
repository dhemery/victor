package com.dhemery.victor;

/**
 * Represents an application running on an iOS device.
 * @author Dale Emery
 *
 */
public interface IosApplication {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

    /**
     * Send a message to the application delegate.
     * @param name the name of the message to send (an Objective-C selector).
     * @param arguments arguments to send with the message.
     * @return the value returned by the application delegate.
     */
    String sendMessage(String name, String... arguments);

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
