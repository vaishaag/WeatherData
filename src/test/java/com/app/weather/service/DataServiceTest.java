package com.app.weather.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.weather.model.Location;
import com.app.weather.model.Position;
import com.app.weather.model.WeatherHistory;

public class DataServiceTest {

	private DataService dataService;

	@Before
	public void setUp() throws Exception {
		dataService = DataService.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		dataService = null;
	}

	@Test
	public void testGetInstance() throws Exception {
		assertFalse(DataService.getInstance() == null);
	}	

	@Test
	public void testGetWeatherHistoryForLocation() throws Exception {
		Location location = new Location("Adelaide", "IDCJDW5002");
    	Position position = new Position();
    	position.setElevation(44.86626434326172);
    	position.setLatitude(-34.9284989);
    	position.setLongitude(138.6007456);
    	location.setPosition(position);
		WeatherHistory weatherHistory = dataService.getWeatherHistoryForLocation(location);
		assertTrue(weatherHistory.getPressure().length > 0);
		assertTrue(weatherHistory.getRelativeHumidity().length > 0);
		assertTrue(weatherHistory.getTemperature().length > 0);
	}
}