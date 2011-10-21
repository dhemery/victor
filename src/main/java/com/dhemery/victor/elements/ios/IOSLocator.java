package com.dhemery.victor.elements.ios;

import com.dhemery.victor.elements.Locator;

public class IOSLocator implements Locator {
	public static class Subtype {
		private final String subtype;

		public Subtype() {
			subtype = "";
		}

		public Subtype(String subtype) {
			this.subtype = String.format(":'%s'", subtype);
		}
		
		@Override
		public String toString() { return subtype; }
	}
	private final String mark;
	private final String subtype;
	private final String type;

	private IOSLocator(String type, Subtype subtype, String mark) {
		this.type = type;
		this.mark = mark;
		this.subtype = subtype.toString();
	}

	public IOSLocator(String type, String subtype, String mark) {
		this(type, new Subtype(subtype), mark);
	}
	
	public IOSLocator(String type, String mark) {
		this(type, new Subtype(), mark);
	}
	
	@Override
	public String toString() {
		return String.format("%s%s marked:'%s'", type, subtype, mark);
	}
}
