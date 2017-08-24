package com.app.weather.model;

import static com.app.weather.util.Utils.listToPrimitiveArray;

import java.util.List;

public class WeatherHistory {
	
	double[] temperature;
	double[] pressure;
	double[] relativeHumidity;

	public WeatherHistory() {
	}

	public double[] getTemperature() {
		return temperature;
	}

	public void setTemperature(double[] temperature) {
		this.temperature = temperature;
	}
	
	public void setTemperature(List<Double> temperatureList) {
		this.temperature = listToPrimitiveArray(temperatureList);
	}

	public double[] getPressure() {
		return pressure;
	}

	public void setPressure(List<Double> pressureList) {
		this.pressure = listToPrimitiveArray(pressureList);
	}
	
	public void setPressure(double[] pressure) {
		this.pressure = pressure;
	}

	public double[] getRelativeHumidity() {
		return relativeHumidity;
	}

	public void setRelativeHumidity(double[] relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	
	public void setRelativeHumidity(List<Double> relativeHumidityList) {
		this.relativeHumidity = listToPrimitiveArray(relativeHumidityList);
	}
	
}
