package com.app.weather.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.weather.model.Location;
import com.app.weather.model.Position;
import com.app.weather.model.WeatherDetails;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class OperationsTest {

	private String configFile = "src/test/resources/locations.json";
	private String outputFileName = "testFileWrite.txt";
	
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    	File file = new File(outputFileName);
    	file.delete();
    }

    @Test
    public void testReadJsonFile() throws Exception {
    	JsonObject jsonObject = Operations.readJsonFile(configFile);
    	JsonArray jsonArray = jsonObject.getAsJsonArray("locations");
    	String locationName = jsonArray.get(0).getAsJsonObject().get("name").getAsString();
    	String iataCode = jsonArray.get(0).getAsJsonObject().get("iatacode").getAsString();
    	
    	assertEquals(1, jsonArray.size());
    	assertEquals("Adelaide", locationName);
    	assertEquals("IDCJDW5002", iataCode);
    }

    @Test
    public void testGetJsonObjectFromURL() throws Exception {
    	String url = "http://maps.googleapis.com/maps/api/geocode/json?address=Adelaide";
    	JsonObject jsonObject = Operations.getJsonObjectFromURL(url);
    	assertTrue(jsonObject != null);
    	assertTrue(jsonObject.get("results") != null);
    }

    @Test
    public void testWriteLocationDetailsToFile() throws Exception {
    	Location location = new Location("Adelaide", "IDCJDW5002");
    	
    	Position position = new Position();
    	position.setElevation(44.86626434326172);
    	position.setLatitude(-34.9284989);
    	position.setLongitude(138.6007456);
    	location.setPosition(position);
    	
    	WeatherDetails weatherDetails = new WeatherDetails(12.5, 544.3, 83);
    	List<WeatherDetails> weatherDetailsList = new ArrayList<>();
    	weatherDetailsList.add(weatherDetails);
    	location.setWeatherDetailsList(weatherDetailsList);
    	
    	List<Location> locationList = new ArrayList<>();
    	locationList.add(location);
		
    	Operations.writeLocationDetailsToFile(locationList, outputFileName);
    	
    	File outputFile = new File(outputFileName);
    	assertTrue(outputFile.exists());
    	assertTrue(outputFile.isFile());
    	assertTrue(outputFile.length() > 0);
    }

    @Test
    public void testGetCSVRecordsFromUrl() throws Exception {
    	String url = "http://www.bom.gov.au/climate/dwo/201608/text/IDCJDW2124.201608.csv";
		Iterable<CSVRecord> csvRecords = Operations.getCSVRecordsFromUrl(url);
		
		assertNotNull(csvRecords);
		assertTrue(csvRecords.iterator().hasNext());
    }
}