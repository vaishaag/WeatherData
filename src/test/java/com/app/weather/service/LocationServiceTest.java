package com.app.weather.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.weather.model.Location;
import com.app.weather.model.Position;

public class LocationServiceTest {

	private LocationService locationService;
	
	private List<Location> expectedList;
	
    @Before
    public void setUp() throws Exception {
    	locationService = new LocationService();
    	expectedList = new ArrayList<>();
    	Location location = new Location("Adelaide", "IDCJDW5002");
    	Position position = new Position();
    	position.setElevation(44.86626434326172);
    	position.setLatitude(-34.9284989);
    	position.setLongitude(138.6007456);
    	location.setPosition(position);
    	expectedList.add(location);
    }

    @After
    public void tearDown() throws Exception {
    	locationService = null;
    }

    @Test
    public void testPopulateAllLocationDetails() throws Exception {
    	String testFile = "src/test/resources/locations-test.json";
    	List<Location> locations = locationService.getLocationListFromConfigFile(testFile);
    	locationService.populateAllLocationDetails(locations);
    	assertEquals(locations.get(0).getName(), expectedList.get(0).getName());
    	assertEquals(locations.get(0).getIataCode(), expectedList.get(0).getIataCode());
    	assertEquals(locations.get(0).getPosition().getElevation(), expectedList.get(0).getPosition().getElevation(), 0);
    	assertEquals(locations.get(0).getPosition().getLatitude(), expectedList.get(0).getPosition().getLatitude(), 0);
    	assertEquals(locations.get(0).getPosition().getLongitude(), expectedList.get(0).getPosition().getLongitude(), 0);
    	System.out.println(locations);
    }
    
    @Test
    public void testGetLocationListFromConfigFile() throws Exception {
    	String testFile = "src/test/resources/locations-test.json";
    	List<Location> locations = locationService.getLocationListFromConfigFile(testFile);
    	assertTrue(locations.size() > 0);
    	assertEquals(locations.get(0).getName(), expectedList.get(0).getName());
    	assertEquals(locations.get(0).getIataCode(), expectedList.get(0).getIataCode());
    }
}