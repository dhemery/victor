package com.dhemery.victor.device;

/**
 * An agent that interacts with an iOS Simulator.
 */
public interface SimulatorAgent {
    /**
     * Start the simulator process.
     */
    void start();

    /**
     * Stop the simulator process.
     */
    void stop();

    /**
     * Invoke a menu item in the simulator.
     *
     * @param menuName     the name of the menu that includes the item.
     * @param menuItemName the name of the menu item to invoke.
     */
    void touchMenuItem(String menuName, String menuItemName);

    /**
     * Invoke a subitem of a menu item in the simulator.
     *
     * @param menuName     the name of the menu that includes the item.
     * @param menuItemName the name of the menu item to invoke.
     * @param menuSubItemName   the name of the menu subitem to invoke.
     */
    void touchMenuItem(String menuName, String menuItemName, String menuSubItemName);
}
