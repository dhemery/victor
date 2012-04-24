package com.dhemery.victor.simulator;


public interface Simulator {
	/**
	 * Invokes a menu item in the simulator.
	 * @param menuName the name of the menu that includes the item
	 * @param menuItemName the name of the menu item to touch
	 */
	public void touchMenuItem(String menuName, String menuItemName);

}
