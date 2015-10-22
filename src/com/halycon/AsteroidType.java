package com.halycon;

public enum AsteroidType {
	
	LARGE(16, 1),
	MEDIUM(8, 2),
	SMALL(4, 4);
	
	private final int value;
	private final int size;
	
	private AsteroidType(final int size, final int value) {
		this.value = value;
		this.size = size;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getSize() {
		return size;
	}
}
