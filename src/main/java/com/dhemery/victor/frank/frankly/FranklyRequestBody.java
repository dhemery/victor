package com.dhemery.victor.frank.frankly;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * The body of a Frank request.
 * Buy default, a RequestBody is empty and is not written to the connection,
 * which causes the request to be sent via HTTP GET.
 * Derived classes may actually write bytes to the connection,
 * which causes the request to be sent vie HTTP PUT.
 * @author Dale Emery
 *
 */
public class FranklyRequestBody {
	/**
	 * Writes the body of this request through the connection.
	 * In this class, the method does nothing, which causes the request to be sent via HTTP GET.
	 * Derived classes may override this method and actually write bytes,
	 * which causes the message to be sent via HTTP PUT.
	 * @param connection the connection to write the request body to.
	 * @throws IOException
	 */
	public void writeTo(HttpURLConnection connection) throws IOException {}

	@Override
	public String toString() {
		return "";
	}
}