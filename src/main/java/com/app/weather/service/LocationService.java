package com.app.weather.service;

import static com.app.weather.util.Constants.ELEVATION_GOOGLE_API;
import static com.app.weather.util.Constants.GEOCODE_GOOGLE_API;
import static com.app.weather.util.Operations.readJsonFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.app.weather.model.Location;
import com.app.weather.util.Operations;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * This class handles all the operations related to a location such as
 * collecting the geographical details and weather details.
 * 
 * @author Vaishaag Subhagan
 */
public class LocationService {

	/**
	 * This method parses the input JSON configuration file and creates the list of
	 * locations
	 * 
	 * @param configFileName
	 *            - the JSON configuration file with all the locations listed
	 * @return the list of <Location> object which consist all the locations
	 *         specified in the configuration file
	 */
	public List<Location> getLocationListFromConfigFile(String configFileName) {
		List<Location> locationList = new ArrayList<>();
		JsonObject config = null;
		try {
			config = readJsonFile(configFileName);
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Failed to read and parse config file " + configFileName);
			System.err.println(e.getMessage());
		}

		JsonArray locationArray = null;
		if (null != config) {
			locationArray = config.getAsJsonObject().getAsJsonArray("locations");
		}

		for (JsonElement locationObj : locationArray) {
			JsonObject jsonObject = locationObj.getAsJsonObject();
			String locationName = jsonObject.get("name").getAsString();
			String iataCode = jsonObject.get("iatacode").getAsString();
			Location location = new Location(locationName, iataCode);
			locationList.add(location);
		}
		return locationList;
	}

	/**
	 * This method retrieves all the geographical details of all location
	 * 
	 * @param locationList
	 *            - the location list with geographical details of all the locations
	 *            filled in
	 */
	public void populateAllLocationDetails(List<Location> locationList) {
		for (Location location : locationList) {
			try {
				populateLatitudeLongitude(location);
				populateElevation(location);
			} catch (Exception e) {
				System.err.println("ERROR: Failed to get geographical details for location " + location.getName());
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * This method retrieves the latitude and longitude of a location from Google
	 * Maps API. Location name is given as input to the Google Maps API which
	 * returns all the details of that location in a JSON format, which is then
	 * parsed to retrieve the required data.
	 * 
	 * @param location
	 *            - the location for the geographical data is to be retrieved
	 * @throws Exception
	 */
	private void populateLatitudeLongitude(Location location) throws Exception {
		String url = GEOCODE_GOOGLE_API + "?address=" + location.getName().replaceAll(" ", "%20");
		JsonObject jsonObject = Operations.getJsonObjectFromURL(url);
		JsonObject latitudeLongitudeJson = jsonObject.getAsJsonObject().getAsJsonArray("results").get(0)
				.getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");
		location.getPosition().setLatitude(latitudeLongitudeJson.get("lat").getAsDouble());
		location.getPosition().setLongitude(latitudeLongitudeJson.get("lng").getAsDouble());
	}

	/**
	 * This method retrieves the elevation of a location from Google Maps API.
	 * Latitude and longitude is given as input to the Google Maps API which returns
	 * details of that location in a JSON format, which is then parsed to
	 * retrieve the required data.
	 * 
	 * @param location
	 *            - the location for the geographical data is to be retrieved
	 * @throws Exception
	 */
	private void populateElevation(Location location) throws Exception {
		double latitude = location.getPosition().getLatitude();
		double longitude = location.getPosition().getLongitude();
		String url = ELEVATION_GOOGLE_API + "?locations=" + latitude + "," + longitude;

		JsonObject jsonObject = Operations.getJsonObjectFromURL(url);
		Double elevation = jsonObject.getAsJsonObject().getAsJsonArray("results").get(0).getAsJsonObject()
				.get("elevation").getAsDouble();
		location.getPosition().setElevation(elevation);
	}

}
