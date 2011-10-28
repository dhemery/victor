package com.dhemery.victor;

import java.io.IOException;

/**
 * A driver that can interact with a running iPhone or other iOS device.
 * @author Dale Emery
 *
 */
public interface PhoneDriver {
	public void rotateLeft();
	public void rotateRight();
	
	/**
	 * Shuts down the phone.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void shutDown() throws IOException, InterruptedException;
}
