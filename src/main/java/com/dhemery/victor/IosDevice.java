package com.dhemery.victor;

/**
 * Represents an iOS device (real or simulated).
 * @author Dale Emery
 *
 */
public interface IosDevice {
	public void rotateLeft();
	public void rotateRight();

	/**
	 * Saves an image of the screen to the desktop.
	 */
	public void saveScreenShot();
}
