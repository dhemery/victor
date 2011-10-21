package com.dhemery.victor.driver.io;


public class Response {
	private final String status;
	private final String body;
	
	public Response(String status, String body) {
		this.status = status;
		this.body = body;		
	}

	public String body() { return body; }
	public String status() { return status; }
}
