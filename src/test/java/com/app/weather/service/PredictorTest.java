package com.app.weather.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.weather.model.Location;
import com.app.weather.model.Position;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class PredictorTest {

	private Predictor predictor;

	@Before
	public void setUp() throws Exception {
		predictor = Predictor.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		predictor = null;
	}

	@Test
	public void testGetInstance() throws Exception {
		assertFalse(Predictor.getInstance() == null);
	}

	@Test
	public void testPredictWeatherParams() throws Exception {
		List<Location> locations = new ArrayList<>();
		Location location = new Location("Adelaide", "IDCJDW5002");
		Position position = new Position();
		position.setElevation(44.86626434326172);
		position.setLatitude(-34.9284989);
		position.setLongitude(138.6007456);
		location.setPosition(position);
		locations.add(location);
		predictor.predictWeatherDetails(locations);
		assertTrue(locations.get(0).getWeatherDetailsList().size() > 0);
		assertTrue(locations.get(0).getWeatherDetailsList().get(0).getCondition() != null);
	}
}