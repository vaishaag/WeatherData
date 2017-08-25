package com.app.weather;

import static com.app.weather.util.Constants.OUTPUT_FILE;

import java.util.List;

import com.app.weather.model.Location;
import com.app.weather.service.LocationService;
import com.app.weather.service.Predictor;
import com.app.weather.util.Operations;

public class Application {

	public static void main(String[] args) throws Exception {
		if (null == args || args.length != 1) {
			System.err.println("ERROR: Config file not specified. Path to config file must be provided as an argument");
			throw new IllegalArgumentException("Config file not specified");
		}
		String configFile = args[0];

		Application weatherPrediction = new Application();
		weatherPrediction.start(configFile);
	}

	public void start(String configFile) {
		LocationService locationService = new LocationService();
		Predictor predictor = Predictor.getInstance();

		List<Location> locationList = locationService.getLocationListFromConfigFile(configFile);
		locationService.populateAllLocationDetails(locationList);
		predictor.predictWeatherDetails(locationList);
		Operations.writeLocationDetailsToFile(locationList, OUTPUT_FILE);
	}
}
