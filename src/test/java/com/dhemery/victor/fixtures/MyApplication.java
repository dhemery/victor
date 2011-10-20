package com.dhemery.victor.fixtures;

import java.io.File;

import com.dhemery.victor.driver.VictorClient;

public class MyApplication extends VictorClient {
	private static final String PATH = new File("example/Test App Frankified.app/Test App Frankified").getAbsolutePath();
	
	public MyApplication() {
		super(PATH);
	}
}
