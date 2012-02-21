package com.dhemery.victor.simulator;


public interface Simulator {
	/**
	 * Launches an application in the simulator.
	 * @param applicationPath the file path of the application to launch.
	 */
	public void launch(String applicationPath, String deviceType);
	
	/**
	 * Shuts down the simulator.
	 */
	public void shutDown();
	
	/**
	 * Invokes a menu item in the simulator.
	 * @param menuName
	 * @param menuItemName
	 */
	public void touchMenuItem(String menuName, String menuItemName);
}