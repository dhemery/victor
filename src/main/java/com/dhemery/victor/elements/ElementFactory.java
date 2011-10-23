package com.dhemery.victor.elements;


public interface ElementFactory {
	public Element element(Locator locator);
	public Element element(String type, String name);
}
