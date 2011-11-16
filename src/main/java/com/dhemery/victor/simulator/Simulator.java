package com.dhemery.victor.simulator;

import java.io.IOException;

public interface Simulator {
	/**
	 * Launches an application in the simulator.
	 * @param applicationPath the file path of the application to launch.
	 * @throws IOException
	 */
	public void launch(String applicationPath) throws IOException;
	
	/**
	 * Shuts down the simulator.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void shutDown() throws IOException, InterruptedException;
	
	/**
	 * Invokes a menu item in the simulator.
	 * @param menuName
	 * @param menuItemName
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void touchMenuItem(String menuName, String menuItemName)throws IOException, InterruptedException;
}