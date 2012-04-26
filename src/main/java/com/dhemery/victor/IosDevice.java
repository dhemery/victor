package com.dhemery.victor;

/**
 * Represents an iOS device (real or simulated).
 * @author Dale Emery
 *
 */
public interface IosDevice {
	void rotateLeft();
	void rotateRight();

	/**
	 * Saves an image of the screen to the desktop.
	 */
	void saveScreenShot();

    /**
     * Starts the device.
     */
    void start();

    /**
     * Stops the device.
     */
    void stop();
}
