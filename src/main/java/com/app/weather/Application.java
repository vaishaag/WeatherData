package com.app.weather;

import static com.app.weather.util.Constants.CONFIG_FILE;
import static com.app.weather.util.Constants.FORECAST_SIZE;
import static com.app.weather.util.Constants.OUTPUT_FILE;
import static com.app.weather.util.Operations.readJsonFile;
import static com.app.weather.util.Operations.writeLocationDetailsToFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.app.weather.model.Location;
import com.app.weather.model.WeatherDetails;
import com.app.weather.model.WeatherHistory;
import com.app.weather.service.DataService;
import com.app.weather.service.LocationService;
import com.app.weather.service.Predictor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Application {

	private List<Location> locationList;

	public static void main(String[] args) throws Exception {
		Application weatherPrediction = new Application();
		weatherPrediction.locationList = new ArrayList<Location>();
		weatherPrediction.start();
	}

	private void start() {
		populateLocationList(CONFIG_FILE);
		LocationService locationService = new LocationService();
		locationService.populateAllLocationDetails(locationList);
		predictWeatherDetails();
		writeLocationDetailsToFile(locationList, OUTPUT_FILE);
	}

	private void populateLocationList(String configFileName) {
		JsonObject config = null;
		try {
			config = readJsonFile(configFileName);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Failed to read and parse config file " + configFileName);
			System.err.println(e.getMessage());
		}

		JsonArray locationArray = config.getAsJsonObject().getAsJsonArray("locations");
		Iterator<JsonElement> iterator = locationArray.iterator();

		while (iterator.hasNext()) {
			JsonObject jsonObject = iterator.next().getAsJsonObject();
			String locationName = jsonObject.get("name").getAsString();
			String iataCode = jsonObject.get("iatacode").getAsString();
			Location location = new Location(locationName, iataCode);
			locationList.add(location);
		}
	}

	private void predictWeatherDetails() {
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
		double[] predictedrelativeHumidity = predictor.predictWeatherParams(history.getRelativeHumidity());
		
		List<WeatherDetails> predictedWeatherDetailsList = new ArrayList<WeatherDetails>();
		for(int i = 0; i < FORECAST_SIZE; i++) {
			WeatherDetails weatherDetails = new WeatherDetails(predictedTemperature[i], predictedPressure[i], predictedrelativeHumidity[i]);
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
		} catch (IOException e) {
			System.err.println("ERROR: Failed to retrieve historical weather data for location " + location.getName());
			System.err.println(e.getMessage());
		}
		return weatherHistory;
	}
}
