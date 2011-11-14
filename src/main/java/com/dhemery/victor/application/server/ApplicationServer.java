package com.dhemery.victor.application.server;

import java.io.IOException;

/**
 * A server that can interact with a running iOS application.
 * @author Dale Emery
 *
 */
public interface ApplicationServer {
	/**
	 * @return an AccessibilityCheckResponse that indicates whether accessibility is enabled in the device.
	 * @throws IOException
	 */
	public AccessibilityCheckResponse accessibilityCheck() throws IOException;

	/**
	 * @return an OrientationResponse that indicates the application's current orientation.
	 * @throws IOException
	 */
	public OrientationResponse orientation() throws IOException;

	/**
	 * Perform an operation on each view that matches a query.
	 * @param query a query that identifies a set of views.
	 * @param operation the operation for each view to perform.
	 * @return a response whose content depends on whether the request succeeds or fails.
	 * @throws IOException
	 */
	public ResultsResponse perform(String query, Operation operation) throws IOException;

	/**
	 * Sends a ping request to the server to determine whether it is receiving and responding to requests.
	 * @return true if the server responds to the ping request, otherwise false.
	 */
	public boolean ping();

}