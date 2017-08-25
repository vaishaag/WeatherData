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

/**
 * This class forecasts the weather. It makes use ARIMA forecasting function for
 * predicting the weather details.
 * 
 * @author Vaishaag Subhagan
 */
public class Predictor {

	private int forecastSize;

	private Predictor() {
		this.forecastSize = FORECAST_SIZE;
	}

	public static Predictor getInstance() {
		return new Predictor();
	}

	/**
	 * This method predicts the weather for all the locations
	 * 
	 * @param locationList
	 *            - the list of locations for which weather is to be predicted
	 */
	public void predictWeatherDetails(List<Location> locationList) {
		for (Location location : locationList) {
			List<WeatherDetails> weatherDetailsList = getPredictedWeatherDetails(location);
			location.setWeatherDetailsList(weatherDetailsList);
		}
	}

	/**
	 * This method predicts the weather for a specific location
	 * 
	 * @param location
	 *            - the location for which the weather is to be predicted
	 * @return the predicted weather details for the predefined number of days
	 */
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

	/**
	 * This method retrieves the historical data for a specified location
	 * 
	 * @param location
	 *            - the location for which historical data is to be retrieved
	 * @return the weather history for the specified location
	 */
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

	/**
	 * This method forecasts the future occurrences given a set of time-series with
	 * constant time-gap. This method is used for predicting the temperature,
	 * relative humidity and pressure for the locations.
	 * 
	 * @param inputData
	 *            - list of double numbers representing time-series with constant
	 *            time-gap
	 * @return the forecasted values
	 */
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
