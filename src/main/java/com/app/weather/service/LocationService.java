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

public class LocationService {
	
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

	private void populateLatitudeLongitude(Location location) throws Exception {
		String url = GEOCODE_GOOGLE_API + "?address=" + location.getName().replaceAll(" ", "%20");
		JsonObject jsonObject = Operations.getJsonObjectFromURL(url);
		JsonObject latitudeLongitudeJson = jsonObject.getAsJsonObject().getAsJsonArray("results").get(0)
				.getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");
		location.getPosition().setLatitude(latitudeLongitudeJson.get("lat").getAsDouble());
		location.getPosition().setLongitude(latitudeLongitudeJson.get("lng").getAsDouble());
	}

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
