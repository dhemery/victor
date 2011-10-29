package com.dhemery.victor.frank;

/**
 * A Frank server response from an HTTP request that return JSON strings.
 * @author Dale Emery
 */
class Response {
	private final String status;
	private final String body;
	
	public Response(String status, String body) {
		this.status = status;
		this.body = body;		
	}

	/**
	 * @return body of the response.
	 */
	public String body() { return body; }
	
	/**
	 * @return the HTTP response status of the response.
	 */
	public String status() { return status; }
	
	@Override
	public String toString() {
		return String.format("[status:%s][body:%s]", status(), body());
	}
}
