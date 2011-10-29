/**
 * 
 */
package com.dhemery.poller;

/**
 * A condition that may be satisfied or not.
 * @author Dale Emery
 *
 */
public abstract class Condition {
	/**
	 * 
	 * @return a description of the condition.
	 */

	public abstract String describe();
	/**
	 * Evaluates whether condition is currently satisfied.
	 * @return whether the condition is currently satisfied.
	 */
	public abstract boolean isSatisfied();
}