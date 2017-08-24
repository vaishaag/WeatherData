package com.app.weather.model;

import static com.app.weather.util.Constants.LINE_SEPERATOR;
import static com.app.weather.util.Constants.SEPERATOR;
import static com.app.weather.util.Utils.formatDecimal;

import java.util.ArrayList;
import java.util.List;

public class Location {

	private String name;
	private String iataCode;
	private Position position;
	private List<WeatherDetails> weatherDetailsList;

	public Location(String locationName, String iataCode) {
		this.name = locationName;
		this.iataCode = iataCode;
		this.position = new Position();
		this.weatherDetailsList = new ArrayList<WeatherDetails>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public List<WeatherDetails> getWeatherDetailsList() {
		return weatherDetailsList;
	}

	public void setWeatherDetailsList(List<WeatherDetails> weatherList) {
		this.weatherDetailsList = weatherList;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (WeatherDetails weather : weatherDetailsList) {
			stringBuilder.append(name + SEPERATOR + position + SEPERATOR + weather.getLocalTime() + SEPERATOR
					+ weather.getCondition().toString() + SEPERATOR + formatDecimal(weather.getTemperature())
					+ SEPERATOR + formatDecimal(weather.getPressure()) + SEPERATOR
					+ formatDecimal(weather.getHumidity()));
			stringBuilder.append(LINE_SEPERATOR);
		}
		return stringBuilder.toString();
	}

}
