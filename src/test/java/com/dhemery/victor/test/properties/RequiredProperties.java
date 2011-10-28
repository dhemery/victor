package com.dhemery.victor.test.properties;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class RequiredProperties {
	Properties properties = new Properties();
	public RequiredProperties(String...propertyFileNames) {
		for(String filename : propertyFileNames) {
			loadProperties(filename);
		}
	}

	public String get(String name) {
		if(properties.containsKey(name)) return properties.getProperty(name);
		throw new RequiredPropertyException(name);
	}

	private void loadProperties(String filename) {
		try {
			InputStream propertiesFile = new FileInputStream(filename);			
			properties.load(propertiesFile);
			propertiesFile.close();
		} catch (Exception e) {
			throw new RequiredPropertyFileException(filename);
		}
	}

	public Integer getInteger(String propertyName) {
		return Integer.parseInt(get(propertyName));
	}
}
