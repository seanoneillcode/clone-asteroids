package com.halycon;

public class ScoreEntry {

	private String name;
	private Integer value;
	
	public ScoreEntry(String name, Integer value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getValue() {
		return value;
	}
}
