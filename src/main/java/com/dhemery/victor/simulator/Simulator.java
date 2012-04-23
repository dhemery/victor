package com.dhemery.victor.simulator;


public interface Simulator {
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
