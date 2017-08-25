package com.app.weather.model;

import static com.app.weather.util.Utils.formatDecimal;

public class Position {

	private double latitude;
	private double longitude;
	private double elevation;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	@Override
	public String toString() {
		return formatDecimal(latitude) + ", " + formatDecimal(longitude) + ", " + formatDecimal(elevation);
	}
}
