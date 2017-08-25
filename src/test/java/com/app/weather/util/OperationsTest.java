package com.app.weather.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static org.junit.Assert.*;

public class OperationsTest {

	private String configFile = "./src/test/resources/locations.json";
	
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

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

    }

    @Test
    public void testWriteLocationDetailsToFile() throws Exception {

    }

    @Test
    public void testGetCSVRecordsFromUrl() throws Exception {

    }
}