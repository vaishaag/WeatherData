package com.app.weather.model;

/**
 * This enum is used to represent the possible weather conditions.
 * 
 * @author Vaishaag Subhagan
 */
public enum Condition {

	RAIN("Rain"), SNOW("Snow"), SUNNY("Sunny");

	private String value;

	Condition(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
