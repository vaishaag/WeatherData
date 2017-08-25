package com.app.weather;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.app.weather.util.Constants;

public class ApplicationTest {
	
	private String configFile = "src/test/resources/locations.json";

	private Application application;
	
    @Rule
    public ExpectedException expected = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		application = new Application();
	}

	@After
	public void tearDown() throws Exception {
		application = null;
	}

	@Test
	public void mainShouldThrowExceptionOnNoArguments() throws Exception {
		expected.expect(IllegalArgumentException.class);
		Application.main(null);
	}

	@Test
	public void startShouldPredictWeatherDataAndWriteToOutputFile() throws Exception {
		application.start(configFile);
		
		File outputFile = new File(Constants.OUTPUT_FILE);
		assertTrue(outputFile.exists());
		assertTrue(outputFile.isFile());
		assertTrue(outputFile.length() != 0);
	}
}