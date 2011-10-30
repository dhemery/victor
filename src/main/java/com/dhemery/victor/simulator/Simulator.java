package com.dhemery.victor.simulator;

import java.io.IOException;

public interface Simulator {
	public void launch(String applicationPath) throws IOException;
	public void shutDown() throws IOException, InterruptedException;
	public void touchMenuItem(String menuName, String menuItemName)throws IOException, InterruptedException;
}