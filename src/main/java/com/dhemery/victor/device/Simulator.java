package com.dhemery.victor.device;


public interface Simulator {
    void start();

    void stop();

    /**
	 * Invokes a menu item in the simulator.
	 * @param menuName the name of the menu that includes the item.
	 * @param menuItemName the name of the menu item to invoke.
	 */
	void touchMenuItem(String menuName, String menuItemName);

}
