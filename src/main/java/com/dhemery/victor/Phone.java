package com.dhemery.victor;

import java.io.IOException;

public interface Phone {
	public void rotateLeft();
	public void rotateRight();
	public void shutDown() throws IOException, InterruptedException;
}
