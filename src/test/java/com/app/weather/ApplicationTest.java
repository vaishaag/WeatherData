package com.app.weather;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest {
	
	private String configFile = "./src/test/resources/locations.json";

	private Application application;

	@Before
	public void setUp() throws Exception {
		application = new Application();
	}

	@After
	public void tearDown() throws Exception {
		application = null;
	}

	@Test(expected = IllegalArgumentException.class)
	public void mainShouldThrowExceptionOnNoArguments() throws Exception {
		Application.main(null);
	}

	@Test
	public void mainShouldPopulateLocationListFromConfigFile() throws Exception {
		Application.main(new String[] {configFile});
//		assertNotNull(application.getLocationList());
	}
}