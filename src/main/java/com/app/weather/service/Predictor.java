package com.app.weather.service;

import static com.app.weather.util.Constants.FORECAST_SIZE;

import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;

public class Predictor {
	
	private int forecastSize;
	private static Predictor instance;
	
	private Predictor() {
		this.forecastSize = FORECAST_SIZE;
	}
	
	public static Predictor getInstance() {
		if(null == instance) {
			instance = new Predictor();
		}
		return instance;
	}

	public double[] predictWeatherParams(double[] inputData) {
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
