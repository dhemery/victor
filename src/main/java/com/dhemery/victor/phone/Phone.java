package com.dhemery.victor.phone;

public interface Phone {
	public enum Orientation {
		LANDSCAPE,
		PORTRAIT,
		UNKNOWN,
	}

	public Orientation orientation();
	public void rotateLeft();
	public void rotateRight();
}
