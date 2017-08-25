package com.app.weather.service;

import static com.app.weather.util.Constants.FORECAST_SIZE;

import java.util.ArrayList;
import java.util.List;

import com.app.weather.model.Location;
import com.app.weather.model.WeatherDetails;
import com.app.weather.model.WeatherHistory;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;

public class Predictor {
	
	private int forecastSize;
	
	private Predictor() {
		this.forecastSize = FORECAST_SIZE;
	}
	
	public static Predictor getInstance() {
		return new Predictor();
	}
	
	public void predictWeatherDetails(List<Location> locationList) {
		for (Location location : locationList) {
			List<WeatherDetails> weatherDetailsList = getPredictedWeatherDetails(location);
			location.setWeatherDetailsList(weatherDetailsList);
		}
	}

	private List<WeatherDetails> getPredictedWeatherDetails(Location location) {
		WeatherHistory history = getHistoricalData(location);

		Predictor predictor = Predictor.getInstance();
		double[] predictedTemperature = predictor.predictWeatherParams(history.getTemperature());
		double[] predictedPressure = predictor.predictWeatherParams(history.getPressure());
		double[] predictedRelativeHumidity = predictor.predictWeatherParams(history.getRelativeHumidity());

		List<WeatherDetails> predictedWeatherDetailsList = new ArrayList<WeatherDetails>();
		for (int i = 0; i < FORECAST_SIZE; i++) {
			WeatherDetails weatherDetails = new WeatherDetails(predictedTemperature[i], predictedPressure[i],
					predictedRelativeHumidity[i]);
			weatherDetails.setLocalTime(weatherDetails.getLocalTime().plusDays(i));
			predictedWeatherDetailsList.add(weatherDetails);
		}
		return predictedWeatherDetailsList;
	}

	private WeatherHistory getHistoricalData(Location location) {
		DataService dataService = DataService.getInstance();
		WeatherHistory weatherHistory = null;
		try {
			weatherHistory = dataService.getWeatherHistoryForLocation(location);
		} catch (Exception e) {
			System.err.println("ERROR: Failed to retrieve historical weather data for location " + location.getName());
			System.err.println(e.getMessage());
		}
		return weatherHistory;
	}
	
	private double[] predictWeatherParams(double[] inputData) {
		int p = 3;
		int d = 0;
		int q = 3;
		int P = 1;
		int D = 1;
		int Q = 0;
		int m = 0;
		ArimaParams arimaParams = new ArimaParams(p, d, q, P, D, Q, m);
		ForecastResult forecastResult = Arima.forecast_arima(inputData, forecastSize, arimaParams);
		double[] forecastData = forecastResult.getForecast();
		return forecastData;
	}

}
