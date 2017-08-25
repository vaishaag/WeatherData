package com.app.weather;

import static com.app.weather.util.Constants.OUTPUT_FILE;

import java.util.List;

import com.app.weather.model.Location;
import com.app.weather.service.LocationService;
import com.app.weather.service.Predictor;
import com.app.weather.util.Operations;

/**
 * This is the main class of weather prediction application.
 *
 * @author Vaishaag Subhagan
 */
public class Application {

	/**
	 * Starting point of the application.
	 *
	 * @param args
	 *            - Config file with the list of locations has to be passed in as
	 *            the only argument
	 */
	public static void main(String[] args) {
		if (null == args || args.length != 1) {
			System.err.println("ERROR: Config file not specified. Path to config file must be provided as an argument");
			throw new IllegalArgumentException("Config file not specified");
		}
		String configFile = args[0];

		Application weatherPrediction = new Application();
		weatherPrediction.start(configFile);
	}

	/**
	 * This method gets the location details from configuration file. Geo location
	 * data for each record is retrieved using Google Maps API. Historical data is
	 * collected for each location from http://www.bom.gov.au. Then weather is
	 * forecasted for the specified number of days using ARIMA.
	 *
	 * @param configFile
	 *            - JSON configuration file name
	 */
	public void start(String configFile) {
		LocationService locationService = new LocationService();
		Predictor predictor = Predictor.getInstance();

		List<Location> locationList = locationService.getLocationListFromConfigFile(configFile);
		locationService.populateAllLocationDetails(locationList);
		predictor.predictWeatherDetails(locationList);
		Operations.writeLocationDetailsToFile(locationList, OUTPUT_FILE);
	}
}
