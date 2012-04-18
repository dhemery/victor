package com.dhemery.victor.simulator;


public interface Simulator {
	/**
	 * Launches an application in the simulator.
	 * @param applicationPath the file path of the application to launch.
	 */
    public void launch(String applicationPath, String sdkVersion, Boolean headless);

        /**
       * Shuts down the simulator.
       */
	public void shutDown();
	
	/**
	 * Invokes a menu item in the simulator.
	 * @param menuName the name of the menu that includes the item
	 * @param menuItemName the name of the menu item to touch
	 */
	public void touchMenuItem(String menuName, String menuItemName);
}