package com.app.weather.model;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class holds the weather details of a location at a particular point in
 * time. It includes local time, temperature, relative humidity, pressure and
 * the weather condition.
 * 
 * @author Vaishaag Subhagan
 */
public class WeatherDetails {

	private LocalDateTime localTime;
	private double temperature;
	private double pressure;
	private double humidity;
	private Condition condition;

	public WeatherDetails(double temperature, double pressure, double humidity) {
		this.temperature = temperature;
		this.pressure = pressure;
		this.humidity = humidity;
		this.localTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0, 0));
		deduceCondition();
	}

	public LocalDateTime getLocalTime() {
		return localTime;
	}

	public void setLocalTime(LocalDateTime localTime) {
		this.localTime = localTime;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	private void deduceCondition() {
		this.condition = Condition.SUNNY;
		if (temperature < 0) {
			this.condition = Condition.SNOW;
		} else if (humidity > 70) {
			this.condition = Condition.RAIN;
		} else {
			this.condition = Condition.SUNNY;
		}
	}
}
